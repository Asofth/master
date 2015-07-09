package util;

import java.util.Properties;

/**
 * Utilitário para varíaveis de ambiente da aplicação
 */
public class Ambiente {

	public enum Atributo {

		JMS_BROKER_URL("jms.broker.url"), JMS_BROKER_JMX_HOST(
				"jms.broker.jmx.host"), JMS_BROKER_USER("jms.broker.user"), JMS_BROKER_PASSWORD(
				"jms.broker.password"), JMS_BROKER_NAME("jms.broker.name"), JMS_BROKER_QUEUE_NAME(
				"jms.broker.queue.name");

		private String value;

		private Atributo(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private static Properties atributosAmbiente = new Properties();
	static {
		try {
			atributosAmbiente.load(Ambiente.class.getClassLoader()
					.getResourceAsStream("ambiente.properties"));
		} catch (Exception e) {
			Log.registrar(e);
		}
	}

	public static String getAtributo(Atributo atributo) {
		return atributosAmbiente.getProperty(atributo.getValue());
	}

	/**
	 * Obtém o nome parametrizado para a instância
	 */
	public static String getNomeInstancia() {
		return System.getProperty("nomeInstancia");
	}

	/**
	 * Indica se a instância deve ser inicializada ativa, executando, ou deve
	 * ficar em espera
	 */
	public static Boolean isInstanciaAtiva() {
		return Boolean.valueOf(System.getProperty("instanciaAtiva"));
	}

}
