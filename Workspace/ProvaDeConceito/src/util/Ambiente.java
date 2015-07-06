package util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utilitário para varíaveis de ambiente da aplicação
 */
public class Ambiente {

	private static Properties propriedadesAmbiente = new Properties();
	static {
		try {
			propriedadesAmbiente.load(new FileInputStream(
					"resource/ambiente.properties"));
		} catch (Exception e) {
			Log.registrar(e);
		}
	}

	/**
	 * Retorna a URL de conexão da fil ade mensagens.
	 */
	public static String getURLBrokerJMS() {
		return propriedadesAmbiente.getProperty("url.bkroker.jms");
	}

	/**
	 * Retorna o nome da fila de mensagens utilizada.
	 */
	public static String getNomeFila() {
		return propriedadesAmbiente.getProperty("nome.fila");
	}

}
