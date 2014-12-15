package monitor.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Computador implements Serializable {

	private static final long serialVersionUID = -3871996646921278670L;

	private String identificador;
	private String endereco;
	private Localidade localidade;
	private List<Propriedade> propriedades;
	private List<Programa> programas;

	public Computador(String identificador, String endereco,
			Localidade localidade) {
		this.identificador = identificador;
		this.endereco = endereco;
		this.localidade = localidade;
		this.propriedades = new ArrayList<Propriedade>();
		this.programas = new ArrayList<Programa>();
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getEndereco() {
		return endereco;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public List<Propriedade> getPropriedades() {
		return propriedades;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

}
