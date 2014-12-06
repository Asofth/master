package asofth.prototype.agent.behaviour.impl;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import asofth.prototype.agent.EventProcessingAgent;
import asofth.prototype.agent.behaviour.Collect;
import asofth.prototype.event.PrimitiveEvent;
import asofth.prototype.util.DFUtils;

/**
 * Default collector implementation, specialized in sending the primitive events
 * to the {@link EventProcessingAgent}
 *
 * @param <T>
 *            Any kind of {@link PrimitiveEvent}
 */
public abstract class AbstractCollectorBehaviour<T extends PrimitiveEvent>
		extends CyclicBehaviour implements Collect<T> {

	private static final long serialVersionUID = -4567656216641219552L;

	@Override
	public abstract T doCollect();

	public abstract Long getIntervalInMilliseconds();

	/**
	 * This method looks up for the {@link EventProcessingAgent} an sends the
	 * primitive event collected.
	 */
	@Override
	public void action() {

		try {
			T primitive = doCollect();
			if (primitive != null) {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContentObject(primitive);
				msg.addReceiver(DFUtils.search(super.myAgent,
						EventProcessingAgent.class));
				super.myAgent.send(msg);
			}

			Long intervalInMillisecons = getIntervalInMilliseconds();
			if (intervalInMillisecons != null) {
				try {
					Thread.sleep(intervalInMillisecons);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
