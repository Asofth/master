package monitor.agente.monitor;

import jade.core.Agent;
import monitor.agente.AgenteControlador;
import monitor.evento.Evento;
import monitor.util.DFUtils;

public abstract class AgenteMonitor<T extends Evento> extends Agent {

	private static final long serialVersionUID = -878260455740458917L;

	public abstract ComportamentoMonitoracao<T>[] definirComportamentos();

	@Override
	protected void setup() {
		super.setup();
		ComportamentoMonitoracao<T>[] comportamentos = definirComportamentos();
		for (ComportamentoMonitoracao<T> comportamento : comportamentos) {
			super.addBehaviour(comportamento);
		}

		DFUtils.register(this, AgenteControlador.SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
