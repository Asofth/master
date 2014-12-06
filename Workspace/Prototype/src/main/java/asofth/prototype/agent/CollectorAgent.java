package asofth.prototype.agent;

import jade.core.Agent;
import asofth.prototype.agent.behaviour.impl.QueueSizeCollectorBehaviour;

/**
 * Responsible for collection primitive events as indicated by its behaviours
 * 
 * @author carlos
 */
public class CollectorAgent extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	@Override
	protected void setup() {
		super.setup();
		
		// TODO: setar o behaviour em runtime
		super.addBehaviour(new QueueSizeCollectorBehaviour());
		
		AgentUtils.register(this);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
	
}
