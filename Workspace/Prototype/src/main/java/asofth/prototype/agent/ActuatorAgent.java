package asofth.prototype.agent;

import monitor.util.DFUtils;
import jade.core.Agent;

/**
 * Responsible for actuating over the environment as indicated by its behaviours
 * 
 * @author carlos
 */
public class ActuatorAgent extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	@Override
	protected void setup() {
		super.setup();
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
