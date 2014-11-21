package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgentTest1 extends Agent {

	private static final long serialVersionUID = 4528875321310778035L;

	public static final Map<String, Agent> AGENTS = new HashMap<String, Agent>();

	public class BehaviourTest extends TickerBehaviour {

		private static final long serialVersionUID = 5073194741729285203L;

		public BehaviourTest(Agent a, long period) {
			super(a, period);
		}

		@Override
		protected void onTick() {
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(">>> " + new Date());
		}

	}

	public class PingPongBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -818636039854164488L;

		@Override
		public void action() {
			System.out.println(super.myAgent.getAID().getName()
					+ " is waiting for messages");
			ACLMessage msgReceived = super.myAgent.receive();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (msgReceived != null) {
				System.out.println(super.myAgent.getAID().getName()
						+ " has just received a message: "
						+ msgReceived.getContent());
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

				for (Agent a : AGENTS.values()) {
					if (a == super.myAgent) {
						continue;
					}
					msg.addReceiver(a.getAID());
					break;
				}

				msg.setContent("Ping".equals(msgReceived.getContent()) ? "Pong"
						: "Ping");
				super.myAgent.send(msg);
			}
		}
	}

	public class StarterBehaviour extends OneShotBehaviour {

		private static final long serialVersionUID = -4843627690955184653L;

		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);

			for (Agent a : AGENTS.values()) {
				if (a == super.myAgent) {
					continue;
				}
				msg.addReceiver(a.getAID());
				break;
			}

			msg.setContent("Ping");
			super.myAgent.send(msg);
		}

	}

	@Override
	protected void setup() {
		System.out.println("Setting up " + super.getAID().getName() + "...");
		// addBehaviour(new BehaviourTest(this, 1000));
		addBehaviour(new StarterBehaviour());
		addBehaviour(new PingPongBehaviour());
		AGENTS.put(super.getAID().getName(), this);
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("Finalizing " + super.getAID().getName() + "...");
	}

}
