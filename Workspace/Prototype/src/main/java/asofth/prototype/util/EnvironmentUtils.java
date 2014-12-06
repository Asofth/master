package asofth.prototype.util;

import java.io.IOException;
import java.util.Properties;

public class EnvironmentUtils {

	public static String getStartupAgentsClasses() {

		try {
			Properties properties = new Properties();
			properties.load(EnvironmentUtils.class.getClassLoader()
					.getResourceAsStream("environment.properties"));
			String agentsStartupClasses = properties
					.getProperty("agents.startup.classes");
			return agentsStartupClasses;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
