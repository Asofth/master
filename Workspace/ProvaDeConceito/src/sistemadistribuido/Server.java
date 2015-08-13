package sistemadistribuido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import sistemadistribuido.consumidor.ExecutorConsulta;
import sistemadistribuido.consumidor.conector.ConectorAtivacao;
import sistemadistribuido.consumidor.conector.ConectorAtivacaoImpl;
import util.Ambiente;
import util.Log;

public class Server {

	/**
	 * Trata requisições socket
	 */
	public static class Requisicao implements Runnable {

		private Socket socket;

		public Requisicao(Socket socket) {
			this.socket = socket;
		}

		@Override
		@SuppressWarnings("unused")
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				loop: for (String line; (line = br.readLine()) != null;) {
					// System.out.println("Line read: " + line);
					break loop;
				}

				Thread.sleep(500 + (new Random()).nextInt(1000));

				socket.getOutputStream().write("OK\n".getBytes());
			} catch (Exception e) {
				Log.registrar(e);
			} finally {
				try {
					socket.close();
				} catch (Exception e) {
					Log.registrar(e);
				}
			}
		}

	}

	/**
	 * Inicializa o servidor socket
	 */
	public static class ServerMainThread implements Runnable {

		private ConectorAtivacao conector = null;

		@Override
		public void run() {

			ServerSocket serverSocket = null;
			try {

				ExecutorService pool = Executors.newFixedThreadPool(10);

				while (!Thread.currentThread().isInterrupted()) {

					if (this.conector.isAtivo() && serverSocket == null) {
						serverSocket = new ServerSocket(8887);
						System.out.println("iniciando conexoes....");
					} else if (!this.conector.isAtivo() && serverSocket != null) {
						serverSocket.close();
						serverSocket = null;
						System.out.println("encerrando conexoes....");
					} else if (this.conector.isAtivo() && serverSocket != null) {
						Socket socket = serverSocket.accept();
						Requisicao req = new Requisicao(socket);
						pool.execute(req);
					}

				}
			} catch (Exception e) {
				Log.registrar(e);
			} finally {
				try {
					if (serverSocket != null) {
						serverSocket.close();
					}
				} catch (Exception e) {
					Log.registrar(e);
				}
			}
		}

	}

	/**
	 * Registra um conector {@link ConectorAtivacaoImpl} para uma instância da
	 * aplicação
	 */
	private static ConectorAtivacao registrarConector(String nomeInstancia,
			boolean instanciaAtiva) {

		ConectorAtivacaoImpl conector = null;

		try {

			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			conector = new ConectorAtivacaoImpl(instanciaAtiva);
			StandardMBean mbean = new StandardMBean(conector,
					ConectorAtivacao.class);
			mbs.registerMBean(mbean, getJMXObjectName(nomeInstancia));

		} catch (Exception e) {
			Log.registrar(e);
		}
		return conector;
	}

	public static ObjectName getJMXObjectName(String nomeInstancia)
			throws MalformedObjectNameException {
		return new ObjectName(ExecutorConsulta.class.getSimpleName() + ":name="
				+ nomeInstancia);
	}

	/**
	 * Torna executável a aplicação. Informar a varíavel de ambiente
	 * {@code ExecutorConsulta.nomeInstancia} para identificar o nome da
	 * instância.
	 */
	public static void main(String[] args) throws IOException {

		ServerMainThread server = new ServerMainThread();
		server.conector = registrarConector(Ambiente.getNomeInstancia(),
				Ambiente.isInstanciaAtiva());
		Thread serverThread = new Thread(server);
		serverThread.setDaemon(false);
		serverThread.start();

		System.out.println("Executor " + server.conector.getNomeInstancia()
				+ " iniciado!");
	}

}
