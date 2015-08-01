package controle.evento;

public class EventoInstanciaAtiva extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	private String identificadorInstanciaAtiva;

	public EventoInstanciaAtiva(String identificador) {
		super(identificador);
	}

	public String getIdentificadorInstanciaAtiva() {
		return identificadorInstanciaAtiva;
	}

}