package controle.agente;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

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
import controle.agente.atuador.ControladorAtuacao;
import controle.dominio.AtributoElementoGerenciado;
import controle.dominio.Estado;
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
			// adiciona regras
			builder.add(
					ResourceFactory
							.newClassPathResource("regrasAtualizacaoEstadosElementosGerenciados.drl"),
					ResourceType.DRL);
			builder.add(
					ResourceFactory
							.newClassPathResource("regrasReconfiguracaoElementosGerenciados.drl"),
					ResourceType.DRL);
			// checa erros
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
			this.session.insert(new AtributoElementoGerenciado("FILA_MENSAGEM",
					Estado.ATIVO));

			this.session.setGlobal("controladorAtuacao",
					new ControladorAtuacao(agente));

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
