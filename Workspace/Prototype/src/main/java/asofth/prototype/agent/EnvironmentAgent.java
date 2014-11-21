package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EnvironmentAgent extends Agent {
	private static final long serialVersionUID = 9015272904954395360L;

	public static final String ENVIRONMENT_NAME = "environment";
	public static final String ENVIRONMENT_TYPE = "environment-type";

	private static int lastAgentQty = 0;

	private Queue<String> queue = new ConcurrentLinkedQueue<String>();

	private synchronized void addMessageToQueue(String message) {
		queue.add(message);
	}

	private synchronized String pollMessageFromQueue() {
		return queue.poll();
	}

	public class ListenerBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -818636039854164488L;

		@Override
		public void action() {

			ACLMessage msgReceived = super.myAgent.receive();

			if (msgReceived != null) {
				System.out.println(super.myAgent.getAID().getName()
						+ " has just received a message: "
						+ msgReceived.getContent());
				((EnvironmentAgent) myAgent).addMessageToQueue(msgReceived
						.getContent());
			}
		}
	}

	public class EnqueueBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -818636039854164488L;

		@Override
		public void action() {

			ACLMessage msgReceived = super.myAgent.receive();

			if (msgReceived != null) {
				System.out.println(super.myAgent.getAID().getName()
						+ " has just received a message: "
						+ msgReceived.getContent());
				((EnvironmentAgent) myAgent).addMessageToQueue(msgReceived
						.getContent());
			}
		}
	}

	public class DequeueBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -818636039854164488L;

		@Override
		public void action() {

			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType(MessageConsumerAgent.CONSUMER_TYPE);
				template.addServices(sd);
				DFAgentDescription[] results = DFService.search(myAgent,
						template);
				if (results.length > 0) {
					String msgContent = ((EnvironmentAgent) myAgent)
							.pollMessageFromQueue();
					if (msgContent == null) {
						return;
					}

					for (DFAgentDescription r : results) {
						msg.addReceiver(r.getName());
					}
					msg.setContent(msgContent);
					super.myAgent.send(msg);
				}
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}

		}
	}

	public class MessageConsumerAgentStarterBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = 7579999965613839782L;

		@Override
		public void action() {

			try {
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType(MessageConsumerAgent.CONSUMER_TYPE);
				template.addServices(sd);

				if (((EnvironmentAgent) myAgent).queue.size() == 0) {
					DFAgentDescription[] results = DFService.search(myAgent,
							template);
					for (DFAgentDescription r : results) {
						((EnvironmentAgent) myAgent).kill(r);
					}
					lastAgentQty = 0;
				} else if (((EnvironmentAgent) myAgent).queue.size() < lastAgentQty) {
					DFAgentDescription[] results = DFService.search(myAgent,
							template);
					if (results.length > 1) {
						((EnvironmentAgent) myAgent).kill(results[0]);
						lastAgentQty--;
					}
				} else if (((EnvironmentAgent) myAgent).queue.size() > lastAgentQty) {
					AgentContainer ac = myAgent.getContainerController();
					String agentName = MessageConsumerAgent.CONSUMER_NAME
							+ UUID.randomUUID().getMostSignificantBits();
					ac.createNewAgent(agentName,
							MessageConsumerAgent.class.getName(), null);
					ac.getAgent(agentName).start();
					lastAgentQty++;
				}

			} catch (FIPAException | ControllerException e) {
				e.printStackTrace();
			}
		}
	}

	private void kill(DFAgentDescription r) throws ControllerException {
		AgentContainer ac = super.getContainerController();
		AgentController a = ac.getAgent(r.getName().getLocalName());
		a.kill();
	}

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new MessageConsumerAgentStarterBehaviour());
		addBehaviour(new EnqueueBehaviour());
		addBehaviour(new DequeueBehaviour());

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(ENVIRONMENT_TYPE);
		sd.setName(ENVIRONMENT_NAME);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
