package controle.agente.sensor;

import jade.core.Agent;
import util.JMXUtil;
import util.DFUtil;
import controle.agente.sensor.comportamento.PublicarEventoPrimitivo;
import controle.evento.EventoNumeroMensagensFila;

public class AgenteSensorFilaMensagem extends Agent {

	private static final long serialVersionUID = 3320202385578532920L;

	public class PublicarEventoComNumeroMensagensFila extends
			PublicarEventoPrimitivo<EventoNumeroMensagensFila> {

		private static final long serialVersionUID = 1200706390207965224L;

		private JMXUtil filaMensagem = new JMXUtil();

		@Override
		public EventoNumeroMensagensFila coletarEvento() {
			return new EventoNumeroMensagensFila(
					super.getNomeElementoGerenciado("SENSOR_"),
					(Long) filaMensagem.invocarMetodoFila(
							JMXUtil.MetodoFila.QUEUE_SIZE, null));
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new PublicarEventoComNumeroMensagensFila());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
