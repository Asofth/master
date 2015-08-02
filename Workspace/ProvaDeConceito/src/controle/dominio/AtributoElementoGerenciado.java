package controle.dominio;

import java.io.Serializable;
import java.util.UUID;

public class AtributoElementoGerenciado implements Serializable {

	private static final long serialVersionUID = -4316744938658315217L;

	private String id = UUID.randomUUID().toString();
	private String identificador;
	private Estado estado;

	public AtributoElementoGerenciado(String identificador, Estado estado) {
		this.identificador = identificador;
		this.estado = estado;
	}

	public String getIdentificador() {
		return identificador;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return ">> id=" + id + ", identificador=" + identificador + ", estado="
				+ estado;
	}

}
