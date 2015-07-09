package sistemadistribuido.produtor.conector;

import util.ActiveMQUtil;
import util.Ambiente;
import util.Log;

public class ConectorProdutorImpl implements ConectorProdutor {

	private ActiveMQUtil activeMQ = new ActiveMQUtil();

	/**
	 * Retorna o nome da instância
	 */
	@Override
	public String getNomeInstancia() {
		return Ambiente.getNomeInstancia();
	}

	@Override
	public Long getNumeroMensagens() {

		try {
			return (Long) this.activeMQ.executeMethodQueueViewMBean(
					ActiveMQUtil.QueueMethod.QUEUE_SIZE, null);
		} catch (Exception e) {
			Log.registrar(e);
		}
		return null;
	}

	@Override
	public Long getNumeroConsumidores() {

		try {
			return (Long) this.activeMQ.executeMethodQueueViewMBean(
					ActiveMQUtil.QueueMethod.CONSUMER_COUNT, null);
		} catch (Exception e) {
			Log.registrar(e);
		}
		return null;
	}

}
