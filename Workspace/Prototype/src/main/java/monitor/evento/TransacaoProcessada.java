package monitor.evento;

import java.util.Date;

import monitor.dominio.Propriedade;

public class TransacaoProcessada extends Evento {

	private static final long serialVersionUID = 7411517271831950810L;

	private long tempoProcessamentoEmMilisegundos;

	public TransacaoProcessada(Propriedade propriedade,
			long tempoProcessamentoEmMilisegundos) {
		super(TransacaoProcessada.class.getSimpleName(),
				"Transacao processada", new Date(), propriedade);
		this.tempoProcessamentoEmMilisegundos = tempoProcessamentoEmMilisegundos;
	}

	public long getTempoProcessamentoEmMilisegundos() {
		return tempoProcessamentoEmMilisegundos;
	}

}
