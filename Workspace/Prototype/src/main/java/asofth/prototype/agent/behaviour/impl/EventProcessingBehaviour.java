package asofth.prototype.agent.behaviour.impl;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;

import java.util.Date;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;

import asofth.prototype.agent.ControllerAgent;
import asofth.prototype.agent.QueueCleanerAgent;
import asofth.prototype.event.PrimitiveEvent;
import asofth.prototype.event.QueueSizePrimitiveEvent;
import asofth.prototype.util.DFUtils;

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
						+ event.getCollectingDateTime() + "): "
						+ event.toString());

				if (event instanceof QueueSizePrimitiveEvent) {
					if (((QueueSizePrimitiveEvent) event).getSize() > 10) {
						AgentContainer ac = super.myAgent
								.getContainerController();
						String agentName = DFUtils.getAgentName(
								QueueCleanerAgent.class,
								ControllerAgent.SESSION_ID);
						try {
							ac.createNewAgent(agentName,
									QueueCleanerAgent.class.getName(), null);
							ac.getAgent(agentName).start();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} catch (UnreadableException | ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		KieBaseConfiguration config = KieServices.Factory.get()
				.newKieBaseConfiguration();
		config.setOption(EventProcessingOption.CLOUD);

	}
}
