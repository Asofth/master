package sistemadistribuido.consumidor.conector;

/**
 * Implementação de conector que permite ativar e inativar uma instância da
 * aplicação {@link ExecutorConsulta}
 */
public class ConectorAtivacaoImpl implements ConectorAtivacao {

	private Boolean ativo = true;

	/**
	 * Permite alterar o estado de funcionamento de uma instância da aplicação
	 * que consome a fila de mensagens.
	 * 
	 * @param ativo
	 *            Se for true, permite a execução da instância; se for false, a
	 *            instância fica inativa.
	 */
	public synchronized void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Retorna o estado da instância (ativa/inativa)
	 */
	public boolean isAtivo() {
		synchronized (this.ativo) {
			return this.ativo;
		}
	}

}
