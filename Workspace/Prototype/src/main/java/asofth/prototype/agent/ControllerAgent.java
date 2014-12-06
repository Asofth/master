package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;

import java.util.StringTokenizer;

import asofth.prototype.util.DFUtils;
import asofth.prototype.util.EnvironmentUtils;

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

		/**
		 * This method initializes the agents as defined in
		 * environment.properties file.
		 */
		@Override
		public void action() {

			try {

				StringTokenizer agentsClasses = new StringTokenizer(
						EnvironmentUtils.getStartupAgentsClasses(), ";");

				AgentContainer ac = super.myAgent.getContainerController();

				while (agentsClasses.hasMoreTokens()) {

					@SuppressWarnings("unchecked")
					Class<? extends Agent> agentClass = (Class<? extends Agent>) Class
							.forName(agentsClasses.nextToken());
					ac.createNewAgent(DFUtils.getAgentName(agentClass),
							agentClass.getName(), null);
					ac.getAgent(DFUtils.getAgentName(agentClass)).start();
				}

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

		DFUtils.register(this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
