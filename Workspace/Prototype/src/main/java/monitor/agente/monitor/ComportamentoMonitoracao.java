package monitor.agente.monitor;

import java.io.IOException;

import asofth.prototype.agent.ControllerAgent;
import asofth.prototype.agent.EventProcessingAgent;
import monitor.evento.Evento;
import monitor.util.DFUtils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public abstract class ComportamentoMonitoracao<T extends Evento> extends
		CyclicBehaviour {

	private static final long serialVersionUID = -3275521612921523352L;

	public abstract T gerarEvento();

	public abstract Long getIntervaloExecucaoEmMilisegundos();

	@Override
	public void action() {
		try {
			T evento = gerarEvento();
			if (evento != null) {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContentObject(evento);
				msg.addReceiver(DFUtils.search(super.myAgent,
						EventProcessingAgent.class, ControllerAgent.SESSION_ID));
				System.out.println("Enviando evento: " + evento.toString());
				super.myAgent.send(msg);
			}

			Long intervaloEmMilisegundos = getIntervaloExecucaoEmMilisegundos();
			if (intervaloEmMilisegundos != null) {
				try {
					Thread.sleep(intervaloEmMilisegundos);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
