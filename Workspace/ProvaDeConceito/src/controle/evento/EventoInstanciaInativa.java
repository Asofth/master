package controle.evento;

public class EventoInstanciaInativa extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	private String identificadorInstanciaInativa;

	public EventoInstanciaInativa(String identificador,
			String identificadorInstanciaInativa) {
		super(identificador);
		this.identificadorInstanciaInativa = identificadorInstanciaInativa;
	}

	public String getIdentificadorInstanciaInativa() {
		return identificadorInstanciaInativa;
	}

	@Override
	public String toString() {
		return "identificadorInstanciaInativa=" + identificadorInstanciaInativa;
	}

}