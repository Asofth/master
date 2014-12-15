package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class DiminuicaoConsumidoresFila extends Evento {

	private static final long serialVersionUID = -4596345169520616827L;

	private int quantidadeConsumidores;

	public DiminuicaoConsumidoresFila(Propriedade propriedade,
			int quantidadeConsumidores) {
		super(
				DiminuicaoConsumidoresFila.class.getSimpleName(),
				"Diminuicao na quantidade de consumidores processando fila de transacoes",
				new Date(), propriedade);
		this.quantidadeConsumidores = quantidadeConsumidores;
	}

	public int getQuantidadeConsumidores() {
		return quantidadeConsumidores;
	}

}
