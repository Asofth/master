package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;

/**
 * Responsible for initializing and controlling the collectors agents and its
 * behaviours
 * 
 * @author carlos
 */
public class ControllerAgent extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	public class Startup extends OneShotBehaviour {

		private static final long serialVersionUID = -1969910869557015465L;

		@Override
		public void action() {

			try {
				AgentContainer ac = super.myAgent.getContainerController();

				// Agente coletor
				ac.createNewAgent(AgentUtils.getAgentName(CollectorAgent.class),
						CollectorAgent.class.getName(), null);
				ac.getAgent(AgentUtils.getAgentName(CollectorAgent.class)).start();

				// Agente analisador eventos
				ac.createNewAgent(AgentUtils.getAgentName(EventProcessingAgent.class),
						EventProcessingAgent.class.getName(), null);
				ac.getAgent(AgentUtils.getAgentName(EventProcessingAgent.class)).start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new Startup());

		AgentUtils.register(this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
