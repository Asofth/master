package controle.evento;

import controle.dominio.IdentificadorAtributoElementoGerenciado;
import controle.dominio.IdentificadorElementoGerenciado;

public class EventoInstanciaAtiva extends EventoPrimitivo {

	private static final long serialVersionUID = 8093831685175655276L;

	private String identificadorInstanciaAtiva;

	public EventoInstanciaAtiva(
			IdentificadorElementoGerenciado identificadorElementoGerenciado,
			IdentificadorAtributoElementoGerenciado identificadorAtributoElementoGerenciado) {
		super(identificadorElementoGerenciado,
				identificadorAtributoElementoGerenciado);
	}

	public String getIdentificadorInstanciaAtiva() {
		return identificadorInstanciaAtiva;
	}

}