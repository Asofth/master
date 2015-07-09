package util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DFUtil {

	private static final String TYPE_DESCRIPTION = "-Type";

	public static String getAgentName(Class<? extends Agent> agentClass,
			long sessionID) {
		return agentClass.getSimpleName() + "_" + sessionID;
	}

	public static String getAgentType(Class<? extends Agent> agentClass,
			long sessionID) {
		return agentClass.getSimpleName() + TYPE_DESCRIPTION + "_" + sessionID;
	}

	public static void register(Agent agent) {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(agent.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getAgentName(agent.getClass(), 1L));
		sd.setType(getAgentType(agent.getClass(), 1L));
		dfd.addServices(sd);
		try {
			DFService.register(agent, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void deregister(Agent agent) {

		try {
			DFService.deregister(agent);
		} catch (FIPAException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static AID search(Agent currentAgent,
			Class<? extends Agent> agentClassToSearch) {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getAgentName(agentClassToSearch, 1L));
		sd.setType(getAgentType(agentClassToSearch, 1L));
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
