package asofth.prototype.agent.behaviour.impl;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.Date;

import asofth.prototype.event.PrimitiveEvent;

public class EventProcessingBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 6434269996196411862L;

	@Override
	public void action() {

		ACLMessage msgReceived = super.myAgent.receive();
		if (msgReceived != null) {
			try {
				PrimitiveEvent event = (PrimitiveEvent) msgReceived
						.getContentObject();
				System.out.println(new Date()
						+ " - Receiving Event (sent from IP "
						+ event.getOriginAddress() + " at "
						+ event.getCollectingDateTime() + ") ="
						+ event.toString());
			} catch (UnreadableException | ClassCastException e) {
				e.printStackTrace();
			}
		}
	}
}
