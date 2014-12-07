package asofth.prototype.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import asofth.prototype.util.EnvironmentUtils.EnvironmentProperties;

public class JMXQueueUtils {

	public enum QueueMethod {

		CONSUMER_QUANTITY, QUEUE_SIZE, SEND_MESSAGE, PURGE;

		public Object executeMethod(QueueViewMBean queueMbean, String param)
				throws Exception {

			switch (this) {
			case CONSUMER_QUANTITY:
				return queueMbean.getConsumerCount();
			case QUEUE_SIZE:
				return queueMbean.getQueueSize();
			case SEND_MESSAGE:
				queueMbean.sendTextMessage(param);
				break;
			case PURGE:
				queueMbean.purge();
				break;
			}
			return null;
		}
	}

	public String getQueueName() {
		return EnvironmentUtils
				.getProperty(EnvironmentProperties.ACTIVE_MQ_QUEUE_NAME);
	}

	public Object executeMethodQueueViewMBean(QueueMethod queueMethod,
			String param) {

		JMXConnector jmxc = null;
		try {
			JMXServiceURL url = new JMXServiceURL(
					EnvironmentUtils
							.getProperty(EnvironmentProperties.ACTIVE_MQ_HOST));
			Map<String, Object> env = new HashMap<String, Object>();
			env.put(JMXConnector.CREDENTIALS,
					new String[] {
							EnvironmentUtils
									.getProperty(EnvironmentProperties.ACTIVE_MQ_USER),
							EnvironmentUtils
									.getProperty(EnvironmentProperties.ACTIVE_MQ_PASSWORD) });

			jmxc = JMXConnectorFactory.connect(url, env);
			jmxc.connect();
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();
			ObjectName name = new ObjectName(
					EnvironmentUtils
							.getProperty(EnvironmentProperties.ACTIVE_MQ_BROKER_NAME));
			BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, name, BrokerViewMBean.class,
							true);

			for (ObjectName queueObjectName : brokerMbean.getQueues()) {
				QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
						.newProxyInstance(connection, queueObjectName,
								QueueViewMBean.class, true);
				if (this.getQueueName().equals(queueMbean.getName())) {
					return queueMethod.executeMethod(queueMbean, param);
				}
			}
			throw new IllegalArgumentException(
					EnvironmentUtils
							.getProperty(EnvironmentProperties.ACTIVE_MQ_BROKER_NAME));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				jmxc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
