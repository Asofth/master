package util;

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

import util.Ambiente.Atributo;

public class ActiveMQUtil {

	public enum QueueMethod {

		CONSUMER_COUNT, QUEUE_SIZE, SEND_MESSAGE, PURGE;

		public Object executeMethod(QueueViewMBean queueMbean, String param)
				throws Exception {

			switch (this) {
			case CONSUMER_COUNT:
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

	public Object executeMethodQueueViewMBean(QueueMethod queueMethod,
			String param) {

		JMXConnector jmxc = null;
		try {
			JMXServiceURL url = new JMXServiceURL(
					Ambiente.getAtributo(Atributo.JMS_BROKER_JMX_HOST));
			Map<String, Object> env = new HashMap<String, Object>();
			env.put(JMXConnector.CREDENTIALS,
					new String[] {
							Ambiente.getAtributo(Atributo.JMS_BROKER_USER),
							Ambiente.getAtributo(Atributo.JMS_BROKER_PASSWORD) });

			jmxc = JMXConnectorFactory.connect(url, env);
			jmxc.connect();
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();
			ObjectName name = new ObjectName(
					Ambiente.getAtributo(Atributo.JMS_BROKER_NAME));
			BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, name, BrokerViewMBean.class,
							true);

			for (ObjectName queueObjectName : brokerMbean.getQueues()) {
				QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
						.newProxyInstance(connection, queueObjectName,
								QueueViewMBean.class, true);
				if (Ambiente.getAtributo(Atributo.JMS_BROKER_QUEUE_NAME)
						.equals(queueMbean.getName())) {
					return queueMethod.executeMethod(queueMbean, param);
				}
			}
			throw new IllegalArgumentException(
					Ambiente.getAtributo(Atributo.JMS_BROKER_NAME));
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
