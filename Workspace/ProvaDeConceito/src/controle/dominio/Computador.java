package controle.dominio;

public class Computador extends ElementoGerenciado {

	private static final long serialVersionUID = -1214878254388302509L;

	private Localidade localidade;

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

}
