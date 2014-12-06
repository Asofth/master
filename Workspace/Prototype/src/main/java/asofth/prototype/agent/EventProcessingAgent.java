package asofth.prototype.agent;

import jade.core.Agent;
import asofth.prototype.agent.behaviour.impl.EventProcessingBehaviour;
import asofth.prototype.util.DFUtils;

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

		DFUtils.register(this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
