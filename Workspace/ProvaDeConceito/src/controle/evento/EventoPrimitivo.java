package controle.evento;

import jade.util.leap.Serializable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public abstract class EventoPrimitivo implements Serializable {

	private static final long serialVersionUID = -347863980913150974L;

	private String identificador;
	private Date dataHora;
	private String ipOrigem;

	public EventoPrimitivo(String identificador) {
		this.identificador = identificador;
		this.dataHora = new Date();
		try {
			this.ipOrigem = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			this.ipOrigem = null;
		}
	}

	public String getIdentificador() {
		return identificador;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public String getIpOrigem() {
		return ipOrigem;
	}

}
