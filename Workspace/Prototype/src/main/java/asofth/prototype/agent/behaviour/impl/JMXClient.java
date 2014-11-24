package asofth.prototype.agent.behaviour.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

public class JMXClient {

	public static void main(String[] args) throws Exception {

		// TODO: informações sobre ambiente em arquivo properties
		
		System.out.println("QUEUE SIZE="
				+ (new JMXClient()).getQueueSize("localhost", 2011, "admin",
						"activemq", "processing"));
		System.out.println("CONSUMER COUNT="
				+ (new JMXClient()).getQueueConsumerCount("localhost", 2011,
						"admin", "activemq", "processing"));
	}

	public long getQueueConsumerCount(String hostname, int port, String user,
			String password, String queueName) throws IOException,
			MalformedURLException, MalformedObjectNameException {

		QueueViewMBean queueMbean = getQueueViewMBean(hostname, port, user,
				password, queueName);
		return queueMbean.getConsumerCount();
	}

	public long getQueueSize(String hostname, int port, String user,
			String password, String queueName) throws IOException,
			MalformedURLException, MalformedObjectNameException {

		QueueViewMBean queueMbean = getQueueViewMBean(hostname, port, user,
				password, queueName);
		return queueMbean.getQueueSize();
	}

	private QueueViewMBean getQueueViewMBean(String hostname, int port,
			String user, String password, String queueName) throws IOException,
			MalformedURLException, MalformedObjectNameException {

		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
				+ hostname + ":" + port + "/jmxrmi");
		Map<String, Object> env = new HashMap<String, Object>();
		env.put(JMXConnector.CREDENTIALS, new String[] { user, password });

		JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
		jmxc.connect();
		MBeanServerConnection connection = jmxc.getMBeanServerConnection();
		ObjectName name = new ObjectName("org.apache.activemq:BrokerName="
				+ hostname + ",Type=Broker");
		BrokerViewMBean brokerMbean = (BrokerViewMBean) MBeanServerInvocationHandler
				.newProxyInstance(connection, name, BrokerViewMBean.class, true);

		for (ObjectName queueObjectName : brokerMbean.getQueues()) {
			QueueViewMBean queueMbean = (QueueViewMBean) MBeanServerInvocationHandler
					.newProxyInstance(connection, queueObjectName,
							QueueViewMBean.class, true);
			if (queueName.equals(queueMbean.getName())) {
				return queueMbean;
			}
		}
		throw new IllegalArgumentException(queueName);
	}

}
