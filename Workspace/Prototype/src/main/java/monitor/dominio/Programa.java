package monitor.dominio;

import java.util.ArrayList;
import java.util.List;

public class Programa {

	private String identificador;
	private String descricao;
	private Sistema sistema;
	private List<Propriedade> propriedades;
	private Computador computador;

	public Programa(String identificador, String descricao, Sistema sistema,
			Computador computador) {
		this.identificador = identificador;
		this.descricao = descricao;
		this.sistema = sistema;
		this.propriedades = new ArrayList<Propriedade>();
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public List<Propriedade> getPropriedades() {
		return propriedades;
	}

	public Computador getComputador() {
		return computador;
	}

}
