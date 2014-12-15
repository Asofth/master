package monitor.dominio;

public class Propriedade {

	private String identificador;
	private String descricao;
	private Computador computador;
	private Programa programa;

	public Propriedade(String identificador, String descricao,
			Computador computador) {
		this.identificador = identificador;
		this.descricao = descricao;
		this.computador = computador;
	}

	public Propriedade(String identificador, String descricao, Programa programa) {
		this.identificador = identificador;
		this.descricao = descricao;
		this.programa = programa;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public Computador getComputador() {
		return computador;
	}

	public Programa getPrograma() {
		return programa;
	}

}
