package controle.evento;

public class EventoInstanciaAtiva extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	private String identificadorInstanciaAtiva;

	public EventoInstanciaAtiva(String identificador,
			String identificadorInstanciaAtiva) {
		super(identificador);
		this.identificadorInstanciaAtiva = identificadorInstanciaAtiva;
	}

	public String getIdentificadorInstanciaAtiva() {
		return identificadorInstanciaAtiva;
	}

	@Override
	public String toString() {
		return "identificadorInstanciaAtiva=" + identificadorInstanciaAtiva;
	}

}