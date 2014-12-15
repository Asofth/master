package monitor.agente.configurador;

import jade.core.Agent;
import monitor.agente.AgenteControlador;
import monitor.util.DFUtils;

public abstract class AgenteConfigurador extends Agent {

	private static final long serialVersionUID = 1842039124572850690L;

	public abstract ComportamentoConfiguracao definirComportamento();

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(definirComportamento());

		DFUtils.register(this, AgenteControlador.SESSION_ID);
	}

	@Override
	protected void finalize() throws Throwable {
		DFUtils.deregister(this);
		super.finalize();
	}

}
