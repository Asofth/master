package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class AumentoConsumidoresFila extends Evento {

	private static final long serialVersionUID = -4596345169520616827L;

	private int quantidadeConsumidores;

	public AumentoConsumidoresFila(Propriedade propriedade,
			int quantidadeConsumidores) {
		super(AumentoConsumidoresFila.class.getSimpleName(),
				"Aumento na quantidade de consumidores da fila de transacoes",
				new Date(), propriedade);
		this.quantidadeConsumidores = quantidadeConsumidores;
	}

	public int getQuantidadeConsumidores() {
		return quantidadeConsumidores;
	}

}
