package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class AumentoTransacoesFila extends Evento {

	private static final long serialVersionUID = 8550874646611865604L;

	private int quantidadeTransacoes;

	public AumentoTransacoesFila(Propriedade propriedade,
			int quantidadeTransacoes) {
		super(
				AumentoTransacoesFila.class.getSimpleName(),
				"Aumento na quantidade de transacoes pendentes na fila de transacoes",
				new Date(), propriedade);
		this.quantidadeTransacoes = quantidadeTransacoes;
	}

	public int getQuantidadeTransacoes() {
		return quantidadeTransacoes;
	}

}
