package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class MessageConsumerAgent extends Agent {
	private static final long serialVersionUID = -4817394233510895905L;

	public static final String CONSUMER_NAME = "consumer";
	public static final String CONSUMER_TYPE = "consumer-type";

	public class ReceiveMessageBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -3629644348288478505L;

		@Override
		public void action() {

			ACLMessage msgReceived = super.myAgent.receive();

			if (msgReceived != null) {
				System.out.println(super.myAgent.getAID().getName()
						+ " has just received a message: "
						+ msgReceived.getContent());
			}
		}

	}

	@Override
	protected void setup() {
		super.setup();

		System.out.println("Agent " + super.getAID().getName() + " started!");

		addBehaviour(new ReceiveMessageBehaviour());

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(CONSUMER_TYPE);
		sd.setName(CONSUMER_NAME);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Agent " + super.getAID().getName() + " finalized!");
	}

}
