package asofth.prototype.util;

import java.io.IOException;
import java.util.Properties;

public class EnvironmentUtils {

	public enum EnvironmentProperties {

		AGENT_STARTUP_CLASSES("agents.startup.classes"), 
		ACTIVE_MQ_HOST("active.mq.host"), 
		ACTIVE_MQ_USER("active.mq.user"), 
		ACTIVE_MQ_PASSWORD("active.mq.password"), 
		ACTIVE_MQ_BROKER_NAME("active.mq.brokerName"), 
		ACTIVE_MQ_QUEUE_NAME("active.mq.queueName");

		private String value;

		private EnvironmentProperties(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	private static Properties properties;
	static {
		try {
			properties = new Properties();
			properties
					.load(EnvironmentUtils.class
							.getClassLoader()
							.getResourceAsStream(
									"environment_"
											+ (System.getProperty("ENV") != null ? System
													.getProperty("ENV")
													: "default")
											+ ".properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String getProperty(EnvironmentProperties property) {
		return properties.getProperty(property.getValue());
	}
}
