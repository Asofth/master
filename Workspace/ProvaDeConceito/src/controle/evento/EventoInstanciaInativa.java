package controle.evento;

import controle.dominio.IdentificadorAtributoElementoGerenciado;
import controle.dominio.IdentificadorElementoGerenciado;

public class EventoInstanciaInativa extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	public EventoInstanciaInativa() {
	}
	
	public EventoInstanciaInativa(
			IdentificadorElementoGerenciado identificadorElementoGerenciado,
			IdentificadorAtributoElementoGerenciado identificadorAtributoElementoGerenciado) {
		super(identificadorElementoGerenciado,
				identificadorAtributoElementoGerenciado);
	}

}