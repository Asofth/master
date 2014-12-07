package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import asofth.prototype.util.DFUtils;
import asofth.prototype.util.JMXQueueUtils;

/**
 * Testing purpose agent, created to populated a JMS Queue randomically
 */
public class RandomMessagingAgent extends Agent {

	private static final long serialVersionUID = -5211434165266452441L;

	public class RandomMessageGenerationBehaviour extends CyclicBehaviour {

		private static final long serialVersionUID = -1969910869557015465L;

		private JMXQueueUtils queueHelper = new JMXQueueUtils();

		/**
		 * This method generates random messages on the monitored JMS Queue.
		 * Only for testing purposes, simulating an application that generates
		 * messages.
		 */
		@Override
		public void action() {

			try {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// generates random Text Messages
				this.queueHelper.sendRandomTextMessage();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new RandomMessageGenerationBehaviour());

		DFUtils.register(this, ControllerAgent.SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
