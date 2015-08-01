package controle.agente.sensor.comportamento;

import jade.core.behaviours.CyclicBehaviour;

public abstract class Comportamento extends CyclicBehaviour {

	private static final long serialVersionUID = 2860181213874735039L;

	private String nomeElementoGerenciado;

	/**
	 * O nome do elemento gerenciado é o mesmo nome do agente, configurado na
	 * inicialização
	 */
	public String getNomeElementoGerenciado(String prefixoAgente) {

		if (this.nomeElementoGerenciado == null) {
			synchronized (this) {
				this.nomeElementoGerenciado = super.getAgent().getAID()
						.getLocalName();
				this.nomeElementoGerenciado = this.nomeElementoGerenciado
						.substring(this.nomeElementoGerenciado
								.indexOf(prefixoAgente)+prefixoAgente.length());
			}
		}
		return this.nomeElementoGerenciado;
	}

}
