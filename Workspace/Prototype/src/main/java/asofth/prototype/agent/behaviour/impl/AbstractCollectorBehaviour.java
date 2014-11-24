package asofth.prototype.agent.behaviour.impl;

import asofth.prototype.agent.behaviour.Collect;
import asofth.prototype.event.PrimitiveEvent;
import jade.core.behaviours.CyclicBehaviour;

public abstract class AbstractCollectorBehaviour<T extends PrimitiveEvent>
		extends CyclicBehaviour implements Collect<T> {

	private static final long serialVersionUID = -4567656216641219552L;

	@Override
	public abstract T doCollect();
	
	public abstract Long getIntervalInMilliseconds();

	@Override
	public void action() {
		
		try {
			T primitive = doCollect();
			if (primitive != null) {
				System.out.println(primitive.toString());
			}
			
			Long intervalInMillisecons = getIntervalInMilliseconds();
			if (intervalInMillisecons != null) {
				try {
					Thread.sleep(intervalInMillisecons);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} finally {
			// 
		}
		
	}

}
