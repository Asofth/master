package controle.evento;

public class EventoNumeroMensagensFila extends EventoPrimitivo {

	private static final long serialVersionUID = 6120581406971018787L;

	private Long numeroMensagensFila;

	public EventoNumeroMensagensFila(String identificador,
			Long numeroMensagensFila) {
		super(identificador);
		this.numeroMensagensFila = numeroMensagensFila;
	}

	public Long getNumeroMensagensFila() {
		return numeroMensagensFila;
	}

	@Override
	public String toString() {
		return "numeroMensagensFila=" + numeroMensagensFila;
	}	
	
}
