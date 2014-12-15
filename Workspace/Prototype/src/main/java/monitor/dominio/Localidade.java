package monitor.dominio;

import java.io.Serializable;

public class Localidade implements Serializable {

	private static final long serialVersionUID = -6140591494085448926L;

	private String nome;

	public Localidade(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
