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

public class JMXQueueUtils {

	private String urlDescription = "service:jmx:rmi:///jndi/rmi://localhost:2011/jmxrmi";
	private String user = "admin";
	private String password = "activemq";
	private String brokerName = "org.apache.activemq:type=Broker,brokerName=localhost";
	private String queueName = "processing";

	public Long getProcessingQueueConsumerCount() {

		JMXConnector jmxc = null;
		try {
			JMXServiceURL url = new JMXServiceURL(urlDescription);
			Map<String, Object> env = new HashMap<String, Object>();
			env.put(JMXConnector.CREDENTIALS, new String[] { user, password });

			jmxc = JMXConnectorFactory.connect(url, env);
			jmxc.connect();
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();
			ObjectName name = new ObjectName(brokerName);
			BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, name, BrokerViewMBean.class,
							true);

			for (ObjectName queueObjectName : brokerMbean.getQueues()) {
				QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
						.newProxyInstance(connection, queueObjectName,
								QueueViewMBean.class, true);
				if (queueName.equals(queueMbean.getName())) {
					return queueMbean.getConsumerCount();
				}
			}
			throw new IllegalArgumentException(queueName);
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

	public Long getProcessingQueueSize() {

		JMXConnector jmxc = null;
		try {
			JMXServiceURL url = new JMXServiceURL(urlDescription);
			Map<String, Object> env = new HashMap<String, Object>();
			env.put(JMXConnector.CREDENTIALS, new String[] { user, password });

			jmxc = JMXConnectorFactory.connect(url, env);
			jmxc.connect();
			MBeanServerConnection connection = jmxc.getMBeanServerConnection();
			ObjectName name = new ObjectName(brokerName);
			BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, name, BrokerViewMBean.class,
							true);

			for (ObjectName queueObjectName : brokerMbean.getQueues()) {
				QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
						.newProxyInstance(connection, queueObjectName,
								QueueViewMBean.class, true);
				if (queueName.equals(queueMbean.getName())) {
					return queueMbean.getQueueSize();
				}
			}
			throw new IllegalArgumentException(queueName);
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

	public String getQueueName() {
		return queueName;
	}

}
