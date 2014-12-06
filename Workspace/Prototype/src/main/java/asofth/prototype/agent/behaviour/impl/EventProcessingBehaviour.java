package asofth.prototype.agent.behaviour.impl;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import asofth.prototype.event.PrimitiveEvent;

public class EventProcessingBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 6434269996196411862L;

	@Override
	public void action() {

		ACLMessage msgReceived = super.myAgent.receive();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (msgReceived != null) {
			try {
				PrimitiveEvent event = (PrimitiveEvent) msgReceived
						.getContentObject();
				System.out.println("Receiving Event =" + event.toString());
			} catch (UnreadableException | ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

}
