package asofth.prototype.agent;

import jade.core.Agent;
import asofth.prototype.agent.behaviour.impl.QueueCleanerBehaviour;
import asofth.prototype.util.DFUtils;

/**
 * Executes the behaviour that removes all the pending messages in the monitored
 * JMS Queue
 */
public class QueueCleanerAgent extends Agent {

	private static final long serialVersionUID = 4166739036173109639L;

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new QueueCleanerBehaviour());

		DFUtils.register(this, ControllerAgent.SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
