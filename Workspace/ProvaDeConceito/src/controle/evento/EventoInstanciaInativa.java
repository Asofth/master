package controle.evento;

public class EventoInstanciaInativa extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	private String identificadorInstanciaInativa;

	public EventoInstanciaInativa(String identificador) {
		super(identificador);
	}

	public String getIdentificadorInstanciaInativa() {
		return identificadorInstanciaInativa;
	}

}