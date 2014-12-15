package asofth.prototype.agent;

import monitor.util.DFUtils;
import jade.core.Agent;
import asofth.prototype.agent.behaviour.impl.EventProcessingBehaviour;

/**
 * Responsible for receiving and analyzing primitive events
 * 
 * @author carlos
 */
public class EventProcessingAgent extends Agent {

	private static final long serialVersionUID = 2876023620673171719L;

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new EventProcessingBehaviour());

		DFUtils.register(this, ControllerAgent.SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
