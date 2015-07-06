package sistemadistribuido.consumidor;

import java.lang.management.ManagementFactory;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import org.apache.activemq.ActiveMQConnectionFactory;

import sistemadistribuido.conector.ConectorAtivacao;
import sistemadistribuido.conector.ConectorAtivacaoImpl;

public class ExecutorConsulta {

	public static class ConsumidorFila implements Runnable, ExceptionListener {

		long inicio = 0;
		Random r = new Random();
		private MessageConsumer consumer = null;
		private Session session = null;
		private Connection connection = null;
		private ConectorAtivacaoImpl conector = null;

		private void setConector(ConectorAtivacaoImpl conector) {
			this.conector = conector;
		}

		private void conectarFila() {
			try {

				if (this.connection == null) {
					// Create a ConnectionFactory
					ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
							"tcp://localhost:61616");

					// Create a Connection
					this.connection = connectionFactory.createConnection();
					this.connection.start();

					this.connection.setExceptionListener(this);
				}

				if (session == null) {
					// Create a Session
					this.session = this.connection.createSession(false,
							Session.AUTO_ACKNOWLEDGE);
				}

				if (this.consumer == null) {
					// Create the destination (Topic or Queue)
					Destination destination = this.session
							.createQueue("requisicoes");

					// Create a MessageConsumer from the Session to the Topic or
					// Queue
					this.consumer = this.session.createConsumer(destination);
				}
			} catch (Exception e) {
				desconectarFila();
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}

		private void desconectarFila() {
			try {
				if (this.consumer != null) {
					this.consumer.close();
					this.consumer = null;
				}
				if (this.session != null) {
					this.session.close();
					this.session = null;
				}
				if (this.connection != null) {
					this.connection.close();
					this.connection = null;
				}
			} catch (JMSException e) {
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}

		public void run() {

			try {

				while (!Thread.currentThread().isInterrupted()) {

					if (this.conector.isAtivo()) {
						conectarFila();
					} else {
						desconectarFila();
						continue;
					}

					inicio = System.currentTimeMillis();

					// Wait for a message
					Message message = this.consumer.receive(1000);

					if (message != null) {

						if (message instanceof TextMessage) {
							TextMessage textMessage = (TextMessage) message;
							String text = textMessage.getText();
							System.out.println("Received: " + text);
						} else {
							System.out.println("Received: " + message);
						}

						Thread.sleep(500 + r.nextInt(1000));

						System.out.println("> tempo="
								+ (System.currentTimeMillis() - inicio));
					}
				}
			} catch (Exception e) {
				desconectarFila();
				System.out.println("Caught: " + e);
				e.printStackTrace();
			}
		}

		public synchronized void onException(JMSException ex) {
			System.out.println("JMS Exception occured.  Shutting down client.");
		}
	}

	private static ConectorAtivacaoImpl registrarConector(String nomeInstancia) {
		ConectorAtivacaoImpl conector = null;
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName nome = new ObjectName("ExecutorConsulta:name="
					+ nomeInstancia);
			conector = new ConectorAtivacaoImpl();
			StandardMBean mbean = new StandardMBean(conector,
					ConectorAtivacao.class);
			mbs.registerMBean(mbean, nome);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conector;
	}

	public static void main(String[] args) {

		String nomeInstancia = System
				.getProperty("ExecutorConsulta.nomeInstancia");
		nomeInstancia = (nomeInstancia != null ? nomeInstancia : "instancia"
				+ (new Random()).nextInt(100));
		ConsumidorFila consumidor = new ConsumidorFila();
		consumidor.setConector(registrarConector(nomeInstancia));
		Thread brokerThread = new Thread(consumidor);
		brokerThread.setDaemon(false);
		brokerThread.start();
	}

}