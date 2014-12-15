package monitor.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sistema implements Serializable {

	private static final long serialVersionUID = 677028224478108263L;

	private String nome;
	private List<Programa> programas;

	public Sistema(String nome) {
		this.nome = nome;
		this.programas = new ArrayList<Programa>();
	}

	public String getNome() {
		return nome;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

}
