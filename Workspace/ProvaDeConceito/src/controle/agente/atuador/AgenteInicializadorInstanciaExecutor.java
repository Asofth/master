package controle.agente.atuador;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import util.DFUtil;
import util.JMXUtil;
import util.Log;
import controle.agente.sensor.comportamento.Comportamento;

public class AgenteInicializadorInstanciaExecutor extends Agent {

	private static final long serialVersionUID = -2117862749424897781L;

	public class InicializarOuPararInstancia extends Comportamento {

		private static final long serialVersionUID = -1969910869557015465L;

		private JMXUtil instancia = new JMXUtil();

		@Override
		public void action() {
			try {
				ACLMessage msgReceived = super.myAgent.receive();
				if (msgReceived != null) {

					String elementoGerenciado = msgReceived.getContent();

					if ((Boolean) instancia
							.invocarMetodoInstanciaExecutorConsulta(
									JMXUtil.MetodoInstancia.IS_ATIVO,
									elementoGerenciado)) {
						return;
					}

					instancia.invocarMetodoInstanciaExecutorConsulta(
							JMXUtil.MetodoInstancia.ATIVAR, elementoGerenciado);
				}
			} catch (Exception e) {
				Log.registrar(e);
			}
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new InicializarOuPararInstancia());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
