package asofth.prototype.util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DFUtils {

	private static final String TYPE_DESCRIPTION = "-Type";

	public static String getAgentName(Class<? extends Agent> agentClass) {
		return agentClass.getSimpleName();
	}

	public static String getAgentType(Class<? extends Agent> agentClass) {
		return agentClass.getSimpleName() + TYPE_DESCRIPTION;
	}

	public static void register(Agent agent) {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(agent.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getAgentName(agent.getClass()));
		sd.setType(getAgentType(agent.getClass()));
		dfd.addServices(sd);
		try {
			DFService.register(agent, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static AID search(Agent currentAgent,
			Class<? extends Agent> agentClassToSearch) {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getAgentName(agentClassToSearch));
		sd.setType(getAgentType(agentClassToSearch));
		dfd.addServices(sd);
		try {
			DFAgentDescription[] results = DFService.search(currentAgent, dfd);
			if (results != null && results.length > 0) {
				return results[0].getName();
			}
		} catch (FIPAException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return null;
	}

}
