package controle.agente.atuador;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import util.DFUtil;
import util.Log;

public class ControladorAtuacao {

	private Agent agenteControlador = null;

	public ControladorAtuacao(Agent agenteControlador) {
		this.agenteControlador = agenteControlador;
	}

	public void ruleFired(Class<? extends Agent> classeAgente, String mensagem) {
		try {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent(mensagem);
			msg.addReceiver(DFUtil.search(this.agenteControlador, classeAgente));
			this.agenteControlador.send(msg);
		} catch (Exception e) {
			Log.registrar(e);
		}
	}

}
