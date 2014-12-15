package monitor.evento;

import java.io.Serializable;
import java.util.Date;

import monitor.dominio.Propriedade;

public abstract class Evento implements Serializable {

	private static final long serialVersionUID = -7514314768826275926L;

	private String identificador;
	private String descricao;
	private Date dataHora;
	private Propriedade propriedade;

	public Evento(String identificador, String descricao, Date dataHora,
			Propriedade propriedade) {
		this.identificador = identificador;
		this.descricao = descricao;
		this.dataHora = dataHora;
		this.propriedade = propriedade;
	}

	public String getIdentificador() {
		return identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public Propriedade getPropriedade() {
		return propriedade;
	}

}
