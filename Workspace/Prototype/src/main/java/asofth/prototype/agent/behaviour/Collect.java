package asofth.prototype.agent.behaviour;

import asofth.prototype.event.PrimitiveEvent;

public interface Collect<T extends PrimitiveEvent> {

	public T doCollect();
	
}
