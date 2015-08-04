package controle.agente.controlador;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;

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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import controle.dominio.Atributo;
import controle.dominio.Computador;
import controle.dominio.Localidade;
import controle.dominio.Programa;
import controle.dominio.SistemaControlado;

@SuppressWarnings("deprecation")
public class AgenteControlador extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	public static final long SESSION_ID = UUID.randomUUID()
			.getLeastSignificantBits();

	private KnowledgeBase knowledgeBase = null;
	private StatefulKnowledgeSession session = null;
	private EntryPoint queueStream = null;

	public class Startup extends OneShotBehaviour {

		private static final long serialVersionUID = -1969910869557015465L;

		/**
		 * This method initializes the agents as defined in
		 * environment.properties file.
		 */
		@Override
		public void action() {

			try {

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
				AgenteControlador.this.knowledgeBase = KnowledgeBaseFactory
						.newKnowledgeBase(kBaseConfig);
				knowledgeBase.addKnowledgePackages(builder
						.getKnowledgePackages());

				KieSessionConfiguration sessionConfig = KieServices.Factory
						.get().newKieSessionConfiguration();
				sessionConfig.setOption(ClockTypeOption.get("realtime"));
				AgenteControlador.this.session = knowledgeBase
						.newStatefulKnowledgeSession(sessionConfig, null);

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
						AgenteControlador.this.session.insert(atributo);
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				// inicializa fluxo de eventos
				AgenteControlador.this.queueStream = AgenteControlador.this.session
						.getEntryPoint("FluxoDeEventos");
				// inicializa agente executor de reconfigurações
				AgentContainer ac = super.myAgent.getContainerController();
				String agentName = DFUtil.getAgentName(
						AgenteExecutorReconfiguracao.class, SESSION_ID);
				ac.createNewAgent(agentName,
						AgenteExecutorReconfiguracao.class.getName(),
						new Object[] { session });
				ac.getAgent(agentName).start();

				// inicializa o agente processador de eventos
				agentName = DFUtil.getAgentName(AgenteProcessadorEvento.class,
						SESSION_ID);
				ac.createNewAgent(agentName,
						AgenteProcessadorEvento.class.getName(), new Object[] {
								session, queueStream });
				ac.getAgent(agentName).start();

			} catch (Exception e) {
				e.printStackTrace();
				super.myAgent.doDelete();
			}
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new Startup());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
