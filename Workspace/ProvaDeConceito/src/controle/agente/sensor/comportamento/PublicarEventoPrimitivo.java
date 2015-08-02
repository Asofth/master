package controle.agente.sensor.comportamento;

import jade.lang.acl.ACLMessage;

import java.io.IOException;

import util.DFUtil;
import util.Log;
import controle.agente.AgenteControlador;
import controle.evento.EventoPrimitivo;

public abstract class PublicarEventoPrimitivo<T extends EventoPrimitivo>
		extends Comportamento {

	private static final long serialVersionUID = -1969910869557015465L;

	@Override
	public void action() {

		try {
			T eventoPrimitivo = coletarEvento();

			if (eventoPrimitivo != null) {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.setContentObject(eventoPrimitivo);
				msg.addReceiver(DFUtil.search(super.myAgent,
						AgenteControlador.class));
//				System.out.println("Publicando Evento: "
//						+ eventoPrimitivo.toString());
				super.myAgent.send(msg);
			}

			Long intervalInMillisecons = getIntervaloExecucaoMilisegundos();
			if (intervalInMillisecons != null) {
				try {
					Thread.sleep(intervalInMillisecons);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			Log.registrar(e);
		}
	}

	public abstract T coletarEvento();

	public abstract long getIntervaloExecucaoMilisegundos();

}
