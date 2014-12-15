package asofth.prototype.agent.behaviour.impl;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentContainer;

import java.util.Date;

import monitor.util.DFUtils;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import asofth.prototype.agent.ControllerAgent;
import asofth.prototype.agent.QueueCleanerAgent;
import asofth.prototype.event.PrimitiveEvent;
import asofth.prototype.event.QueueSizePrimitiveEvent;

public class EventProcessingBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 6434269996196411862L;

	private KnowledgeBase knowledgeBase = null;
	private StatefulKnowledgeSession session = null;

	public EventProcessingBehaviour() {
		super();

		KnowledgeBuilder builder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		builder.add(ResourceFactory.newClassPathResource("rules.drl"),
				ResourceType.DRL);
		if (builder.hasErrors()) {
			throw new RuntimeException(builder.getErrors().toString());
		}

		KieBaseConfiguration kBaseConfig = KieServices.Factory.get()
				.newKieBaseConfiguration();
		kBaseConfig.setOption(EventProcessingOption.STREAM);

		this.knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase(kBaseConfig);
		knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());

		KieSessionConfiguration sessionConfig = KieServices.Factory.get()
				.newKieSessionConfiguration();
		sessionConfig.setOption(ClockTypeOption.get("realtime"));

		this.session = knowledgeBase.newStatefulKnowledgeSession(sessionConfig,
				null);

	}

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
						
						EntryPoint queueStream = this.session.getEntryPoint("QueueStream");
						queueStream.insert(event);
						this.session.fireAllRules();

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

}
