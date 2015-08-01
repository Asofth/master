package controle.dominio;

import java.io.Serializable;

public class AtributoElementoGerenciado implements Serializable {

	private static final long serialVersionUID = -4316744938658315217L;

	private String identificador;
	private Estado estado;

	public AtributoElementoGerenciado(String identificador, Estado estado) {
		this.identificador = identificador;
		this.estado = estado;
		System.out.println("Elemento " + this.identificador + "criado! ");
	}

	public String getIdentificador() {
		return identificador;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
		System.out.println("Elemento " + this.identificador
				+ "atualizado: novo estado " + this.estado);
	}

}
