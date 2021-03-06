package controle.dominio;

import java.io.Serializable;

public class Atributo implements Serializable {

	private static final long serialVersionUID = 657199017949452555L;

	private IdentificadorAtributoElementoGerenciado identificador;
	private String descricao;
	private String valorAtual;
	private Estado estadoAtual;
	private Computador computador;
	private Programa programa;

	public IdentificadorAtributoElementoGerenciado getIdentificador() {
		return identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(String valorAtual) {
		this.valorAtual = valorAtual;
	}

	public Estado getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(Estado estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public void setComputador(Computador computador) {
		this.computador = computador;
	}

	public Computador getComputador() {
		return computador;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Programa getPrograma() {
		return programa;
	}

	@Override
	public String toString() {
		return "identificador="
				+ this.identificador
				+ (programa != null ? ", programa[" + programa.toString()
						: "], computador[" + computador.toString())
				+ "], valorAtual=" + this.valorAtual + ", estadoAtual="
				+ this.estadoAtual;
	}
}
