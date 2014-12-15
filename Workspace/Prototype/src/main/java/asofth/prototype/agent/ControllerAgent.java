package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;

import java.util.StringTokenizer;
import java.util.UUID;

import monitor.util.DFUtils;
import monitor.util.EnvironmentUtils;
import monitor.util.EnvironmentUtils.EnvironmentProperties;

/**
 * Responsible for initializing and controlling the collectors agents and its
 * behaviours
 * 
 * @author carlos
 */
public class ControllerAgent extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	public static final long SESSION_ID = UUID.randomUUID().getLeastSignificantBits();
	
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
						EnvironmentUtils
								.getProperty(EnvironmentProperties.AGENT_STARTUP_CLASSES),
						";");

				AgentContainer ac = super.myAgent.getContainerController();

				while (agentsClasses.hasMoreTokens()) {

					@SuppressWarnings("unchecked")
					Class<? extends Agent> agentClass = (Class<? extends Agent>) Class
							.forName(agentsClasses.nextToken());
					String agentName = DFUtils.getAgentName(agentClass, SESSION_ID);
					ac.createNewAgent(agentName, agentClass.getName(), null);
					ac.getAgent(agentName).start();
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

		DFUtils.register(this, SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
