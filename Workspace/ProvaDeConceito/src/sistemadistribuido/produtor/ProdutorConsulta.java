package sistemadistribuido.produtor;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import util.Ambiente;
import util.Log;

/**
 * Aplicação produtora de mensagens.
 */
public class ProdutorConsulta {

	/**
	 * Implementação de cliente para simular cenário de negócio.
	 */
	public static class ProdutorFila implements Runnable, ExceptionListener {

		// altera o intervalo a cada um minuto de execução
		private long tempoEntreMudancaEmMilisegundos = (1 * 60 * 1000);
		private long[] intervaloEntreMensagensEmMilisegundos = { 2000l, 1000l,
				500l, 1000l };
		private int posIntervaloAtual = 0;
		private long ultimaMudanca = System.currentTimeMillis();

		/**
		 * Retorna o intervalo de tempo entre mensagens a ser utilizado na
		 * simulação do cenário de negócio. Este intervalo alterna de tempo em
		 * tempo para simular vários volumes.
		 */
		private long getIntervaloTempoAtual() {

			if ((ultimaMudanca + tempoEntreMudancaEmMilisegundos) < System
					.currentTimeMillis()) {
				posIntervaloAtual++;
				if (posIntervaloAtual >= intervaloEntreMensagensEmMilisegundos.length) {
					posIntervaloAtual = 0;
				}
				ultimaMudanca = System.currentTimeMillis();
			}

			return intervaloEntreMensagensEmMilisegundos[posIntervaloAtual];
		}

		/**
		 * Cria mensagens na fila com delay que varia entre 100ms, 500ms e
		 * 1000ms entre mensagens, simulando cenários de negócio com volumes
		 * distintos. O delay entre mensagens muda a cada 1 minuto.
		 */
		public void run() {

			Connection conexao = null;
			Session sessao = null;
			MessageProducer produtor = null;

			try {

				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
						Ambiente.getURLBrokerJMS());
				conexao = connectionFactory.createConnection();
				conexao.start();
				sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);

				Destination destination = sessao.createQueue(Ambiente
						.getNomeFila());
				produtor = sessao.createProducer(destination);
				produtor.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				String textoMensagem = null;

				while (!Thread.currentThread().isInterrupted()) {

					Thread.sleep(this.getIntervaloTempoAtual());

					textoMensagem = "Requisicao=" + UUID.randomUUID();
					produtor.send(sessao.createTextMessage(textoMensagem));
					System.out.println("[" + (this.getIntervaloTempoAtual())
							+ "] Enviado: " + textoMensagem);
				}

			} catch (Exception e) {
				Log.registrar(e);
			} finally {
				try {
					produtor.close();
					sessao.close();
					conexao.close();
				} catch (Exception ex) {
					Log.registrar(ex);
				}
			}
		}

		public synchronized void onException(JMSException e) {
			Log.registrar(e);
		}

	}

	/**
	 * Torna executável a aplicação.
	 */
	public static void main(String[] args) {

		Thread brokerThread = new Thread(new ProdutorFila());
		brokerThread.setDaemon(false);
		brokerThread.start();
	}

}
