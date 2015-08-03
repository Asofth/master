package controle.dominio;

import java.io.Serializable;

public class Localidade implements Serializable {

	private static final long serialVersionUID = -2360710198518220491L;

	private String nome;

	public Localidade(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
