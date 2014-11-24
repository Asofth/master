package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
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

				String agentName = CollectorAgent.class.getName();
				ac.createNewAgent(agentName, CollectorAgent.class.getName(),
						null);
				ac.getAgent(agentName).start();			

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new Startup());

		this.register(this, "Controller", "Controller-Type");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	private void register(Agent agent, String name, String type) {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(agent.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(name);
		sd.setType(type);
		dfd.addServices(sd);
		try {
			DFService.register(agent, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
