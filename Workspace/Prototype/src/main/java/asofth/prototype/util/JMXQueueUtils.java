package asofth.prototype.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	public Long getProcessingQueueConsumerCount() {

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
					return queueMbean.getConsumerCount();
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

	public Long getProcessingQueueSize() {

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
					return queueMbean.getQueueSize();
				}
			}
			throw new IllegalArgumentException(this.getQueueName());
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
		return EnvironmentUtils
				.getProperty(EnvironmentProperties.ACTIVE_MQ_QUEUE_NAME);
	}

	public void sendRandomTextMessage() {

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
					queueMbean.sendTextMessage(UUID.randomUUID().toString());
					return;
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
	}

	public void purgeQueue() {

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
					queueMbean.purge();
					return;
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
	}

}
