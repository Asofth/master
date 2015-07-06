package util;

public class Log {

	/**
	 * Registra a exceção no próprio console
	 */
	public static void registrar(Exception e) {
		System.out.println("Excecao: " + e);
		e.printStackTrace();
	}
}
