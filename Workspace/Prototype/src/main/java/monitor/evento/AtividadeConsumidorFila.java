package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class AtividadeConsumidorFila extends Evento {

	private static final long serialVersionUID = -321848178677383710L;

	private boolean ativo;
	private int quantidadeMaximaThreads;
	private int quantidadeAtualThreadsEmUso;

	public AtividadeConsumidorFila(Propriedade propriedade, boolean ativo,
			int quantidadeMaximaThreads, int quantidadeAtualThreadsEmUso) {
		super(AtividadeConsumidorFila.class.getSimpleName(),
				"Atividade Consumidor Fila", new Date(), propriedade);
		this.ativo = ativo;
		this.quantidadeMaximaThreads = quantidadeMaximaThreads;
		this.quantidadeAtualThreadsEmUso = quantidadeAtualThreadsEmUso;
	}

	public boolean isAtivo() {
		return this.ativo;
	}

	public int getQuantidadeMaximaThreads() {
		return quantidadeMaximaThreads;
	}

	public int getQuantidadeAtualThreadsEmUso() {
		return quantidadeAtualThreadsEmUso;
	}

}
