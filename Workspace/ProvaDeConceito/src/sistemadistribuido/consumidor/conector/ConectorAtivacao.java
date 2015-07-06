package sistemadistribuido.consumidor.conector;

/**
 * Definição de conector que permite ativar e inativar uma instância da
 * aplicação {@link ExecutorConsulta}
 */
public interface ConectorAtivacao {

	public void setAtivo(boolean ativo);

}
