package conector;

public class ConectorAtivacaoImpl implements ConectorAtivacao {

	private Boolean ativo = false;

	public synchronized void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isAtivo() {
		synchronized (this.ativo) {
			return this.ativo;
		}
	}

}
