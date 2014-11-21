package asofth.prototype.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.UUID;

public class MessageProducerAgent extends Agent {
	private static final long serialVersionUID = -695415568387242493L;

	public class SendMessageBehaviour extends CyclicBehaviour {
		private static final long serialVersionUID = -818636039854164488L;

		@Override
		public void action() {

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType(EnvironmentAgent.ENVIRONMENT_TYPE);
				template.addServices(sd);
				DFAgentDescription[] results = DFService.search(myAgent,
						template);
				for (DFAgentDescription r : results) {
					msg.addReceiver(r.getName());
				}
				msg.setContent(UUID.randomUUID().toString());
				super.myAgent.send(msg);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
	}

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new SendMessageBehaviour());
	}

}
