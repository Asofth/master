package controle.dominio;

public enum IdentificadorElementoGerenciado {

	FILA_MENSAGEM, EXECUTOR_INSTANCIA_1, EXECUTOR_INSTANCIA_2, EXECUTOR_INSTANCIA_3;

	public static IdentificadorElementoGerenciado getByName(String name) {

		for (IdentificadorElementoGerenciado identificador : values()) {
			if (identificador.toString().equals(name)) {
				return identificador;
			}
		}

		return null;
	}
	
}
