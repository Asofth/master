package asofth.prototype.agent;

import jade.core.Agent;
import asofth.prototype.agent.behaviour.impl.QueueConsumerQuantityCollectorBehaviour;
import asofth.prototype.agent.behaviour.impl.QueueSizeCollectorBehaviour;
import asofth.prototype.util.DFUtils;

/**
 * Responsible for collecting primitive events from the monitored Queue
 * 
 * @author carlos
 */
public class QueueStatisticsCollectorAgent extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new QueueSizeCollectorBehaviour());
		super.addBehaviour(new QueueConsumerQuantityCollectorBehaviour());

		DFUtils.register(this);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

}
