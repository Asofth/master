package monitor.dominio;

public class Estado {

	private Propriedade propriedade;
	private Valor atual;

	public enum Valor {

		INATIVO, NORMAL, ALERTA, CRITICO;
	}

	public Estado(Propriedade propriedade) {

		this.propriedade = propriedade;
		this.atual = Valor.INATIVO;
	}

	public void setAtual(Valor atual) {
		this.atual = atual;
	}

	public Valor getAtual() {
		return atual;
	}

	public Propriedade getPropriedade() {
		return propriedade;
	}

}
