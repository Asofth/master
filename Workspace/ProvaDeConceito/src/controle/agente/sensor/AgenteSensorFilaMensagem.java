package controle.agente.sensor;

import jade.core.Agent;
import util.DFUtil;
import controle.agente.sensor.comportamento.PublicarEventoPrimitivo;
import controle.evento.EventoNumeroMensagensFila;

public class AgenteSensorFilaMensagem extends Agent {

	private static final long serialVersionUID = 3320202385578532920L;

	public class NumeroMensagensFila extends
			PublicarEventoPrimitivo<EventoNumeroMensagensFila> {

		private static final long serialVersionUID = 1200706390207965224L;

		/**
		 * TODO: implementar a geração do evento a partir do numero de mensagens
		 * na fila
		 */
		@Override
		public EventoNumeroMensagensFila coletarEvento() {
			return new EventoNumeroMensagensFila("ID", 1000L);
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new NumeroMensagensFila());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
