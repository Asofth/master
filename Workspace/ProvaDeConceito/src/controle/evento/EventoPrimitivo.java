package controle.evento;

import jade.util.leap.Serializable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import controle.dominio.IdentificadorAtributoElementoGerenciado;
import controle.dominio.IdentificadorElementoGerenciado;

public abstract class EventoPrimitivo implements Serializable {

	private static final long serialVersionUID = -347863980913150974L;

	private IdentificadorElementoGerenciado identificadorElementoGerenciado;
	private IdentificadorAtributoElementoGerenciado identificadorAtributoElementoGerenciado;
	private Date dataHora;
	private String ipOrigem;

	public EventoPrimitivo() {
	}
	
	public EventoPrimitivo(
			IdentificadorElementoGerenciado identificadorElementoGerenciado,
			IdentificadorAtributoElementoGerenciado identificadorAtributoElementoGerenciado) {
		this.identificadorElementoGerenciado = identificadorElementoGerenciado;
		this.identificadorAtributoElementoGerenciado = identificadorAtributoElementoGerenciado;
		this.dataHora = new Date();
		try {
			this.ipOrigem = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			this.ipOrigem = null;
		}
	}

	public void setIdentificadorElementoGerenciado(
			IdentificadorElementoGerenciado identificadorElementoGerenciado) {
		this.identificadorElementoGerenciado = identificadorElementoGerenciado;
	}

	public IdentificadorElementoGerenciado getIdentificadorElementoGerenciado() {
		return identificadorElementoGerenciado;
	}

	public void setIdentificadorAtributoElementoGerenciado(
			IdentificadorAtributoElementoGerenciado identificadorAtributoElementoGerenciado) {
		this.identificadorAtributoElementoGerenciado = identificadorAtributoElementoGerenciado;
	}

	public IdentificadorAtributoElementoGerenciado getIdentificadorAtributoElementoGerenciado() {
		return identificadorAtributoElementoGerenciado;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setIpOrigem(String ipOrigem) {
		this.ipOrigem = ipOrigem;
	}

	public String getIpOrigem() {
		return ipOrigem;
	}

}
