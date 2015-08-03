package controle.agente;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import util.DFUtil;
import util.Log;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import controle.agente.atuador.ControladorAtuacao;
import controle.dominio.Atributo;
import controle.dominio.Computador;
import controle.dominio.Localidade;
import controle.dominio.Programa;
import controle.dominio.SistemaControlado;
import controle.evento.EventoPrimitivo;

@SuppressWarnings("deprecation")
public class AgenteControlador extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	public static final long SESSION_ID = UUID.randomUUID()
			.getLeastSignificantBits();

	public class ProcessadorEvento extends CyclicBehaviour {

		private static final long serialVersionUID = -1969910869557015465L;

		private KnowledgeBase knowledgeBase = null;
		private StatefulKnowledgeSession session = null;
		private EntryPoint queueStream = null;

		public ProcessadorEvento(AgenteControlador agente) {
			super();

			KnowledgeBuilder builder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();
			
			// adiciona bases de regras
			builder.add(
					ResourceFactory
							.newClassPathResource("regrasAtualizacaoEstadosElementosGerenciados.drl"),
					ResourceType.DRL);
			builder.add(
					ResourceFactory
							.newClassPathResource("regrasReconfiguracaoElementosGerenciados.drl"),
					ResourceType.DRL);
			if (builder.hasErrors()) {
				throw new RuntimeException(builder.getErrors().toString());
			}
			KieBaseConfiguration kBaseConfig = KieServices.Factory.get()
					.newKieBaseConfiguration();
			kBaseConfig.setOption(EventProcessingOption.STREAM);
			this.knowledgeBase = KnowledgeBaseFactory
					.newKnowledgeBase(kBaseConfig);
			knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

			KieSessionConfiguration sessionConfig = KieServices.Factory.get()
					.newKieSessionConfiguration();
			sessionConfig.setOption(ClockTypeOption.get("realtime"));
			this.session = knowledgeBase.newStatefulKnowledgeSession(
					sessionConfig, null);

			// inicializa elementos gerenciados
			XStream stream = new XStream(new DomDriver());
			stream.alias("SistemaControlado", SistemaControlado.class);
			stream.alias("Programa", Programa.class);
			stream.alias("Computador", Computador.class);
			stream.alias("Atributo", Atributo.class);
			stream.alias("Localidade", Localidade.class);

			try {
				SistemaControlado sistemaControlado = (SistemaControlado) stream
						.fromXML(ResourceFactory.newClassPathResource(
								"dominio.xml").getInputStream());
				sistemaControlado.validar();
				List<Atributo> atributos = sistemaControlado
						.getListaAtributosGerenciados();
				for (Atributo atributo : atributos) {
					this.session.insert(atributo);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			// inicializa o controlador de atuação
			this.session.setGlobal("controladorAtuacao",
					new ControladorAtuacao(agente));

			// inicializa fluxo de eventos
			this.queueStream = this.session.getEntryPoint("FluxoDeEventos");

			// inicializa em outra thread a avaliação dos eventos
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						AgenteControlador.ProcessadorEvento.this.session
								.fireAllRules();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							Log.registrar(e);
						}
					}
				}
			}).start();

		}

		/**
		 * This method initializes the agents as defined in
		 * environment.properties file.
		 */
		@Override
		public void action() {

			try {

				ACLMessage msgReceived = super.myAgent.receive();
				if (msgReceived != null) {

					EventoPrimitivo evento = (EventoPrimitivo) msgReceived
							.getContentObject();
					// System.out.println(new Date() +
					// " - Evento enviado do IP "
					// + evento.getIpOrigem() + " em "
					// + evento.getDataHora() + "): "
					// + evento.getIdentificador() + " - "
					// + evento.toString());

					if (evento instanceof EventoPrimitivo) {
						// System.out.println(evento.toString());
						queueStream.insert(evento);
					}
				}

			} catch (Exception e) {
				Log.registrar(e);
			}
		}

	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new ProcessadorEvento(this));

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
