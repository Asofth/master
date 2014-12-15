package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class DiminuicaoTransacoesFila extends Evento {

	private static final long serialVersionUID = 2642030520910705095L;

	private int quantidadeTransacoes;

	public DiminuicaoTransacoesFila(Propriedade propriedade,
			int quantidadeTransacoes) {
		super(DiminuicaoTransacoesFila.class.getSimpleName(),
				"Aumento no tamanho da fila de transacoes", new Date(),
				propriedade);
		this.quantidadeTransacoes = quantidadeTransacoes;
	}

	public int getQuantidadeTransacoes() {
		return quantidadeTransacoes;
	}

}
