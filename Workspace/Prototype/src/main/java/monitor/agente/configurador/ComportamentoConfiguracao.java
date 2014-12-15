package monitor.agente.configurador;

import jade.core.behaviours.OneShotBehaviour;

public abstract class ComportamentoConfiguracao extends OneShotBehaviour {

	private static final long serialVersionUID = -415253194091804427L;

	public abstract void configurar();

	@Override
	public void action() {
		System.out.println("Executando configuracao");
		this.configurar();
	}

}
