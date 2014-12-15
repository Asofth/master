package monitor.dominio;

import java.io.Serializable;

import monitor.agente.configurador.AgenteConfigurador;

public class Acao implements Serializable {

	private static final long serialVersionUID = 5293350462514368613L;

	private String identificador;
	private String descricao;
	private Programa programa;
	private Class<? extends AgenteConfigurador> agenteConfigurador;

	public Acao(String identificador, String descricao, Programa programa,
			Class<? extends AgenteConfigurador> agenteConfigurador) {
		this.identificador = identificador;
		this.descricao = descricao;
		this.programa = programa;
		this.agenteConfigurador = agenteConfigurador;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public Programa getPrograma() {
		return programa;
	}

	public Class<? extends AgenteConfigurador> getAgenteConfigurador() {
		return agenteConfigurador;
	}

}
