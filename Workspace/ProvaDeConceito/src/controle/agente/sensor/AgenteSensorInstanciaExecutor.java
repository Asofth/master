package controle.agente.sensor;

import jade.core.Agent;
import util.DFUtil;
import util.JMXUtil;
import controle.agente.sensor.comportamento.PublicarEventoPrimitivo;
import controle.evento.EventoInstanciaAtiva;
import controle.evento.EventoInstanciaInativa;

public class AgenteSensorInstanciaExecutor extends Agent {

	private static final long serialVersionUID = 4585767475494660603L;

	public class PublicarEventoInstanciaAtiva extends
			PublicarEventoPrimitivo<EventoInstanciaAtiva> {

		private static final long serialVersionUID = -3446027377283448304L;

		private JMXUtil instancia = new JMXUtil();

		@Override
		public EventoInstanciaAtiva coletarEvento() {

			Boolean isAtivo = false;
			try {
				isAtivo = (Boolean) instancia
						.invocarMetodoInstanciaExecutorConsulta(
								JMXUtil.MetodoInstancia.IS_ATIVO,
								super.getNomeElementoGerenciado("SENSOR_"));
			} catch (RuntimeException e) {
				isAtivo = false;
			}
			if (isAtivo != null && isAtivo) {
				return new EventoInstanciaAtiva(
						super.getNomeElementoGerenciado("SENSOR_"));
			}
			return null;
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	public class PublicarEventoInstanciaInativa extends
			PublicarEventoPrimitivo<EventoInstanciaInativa> {

		private static final long serialVersionUID = 2564307284783450889L;

		private JMXUtil instancia = new JMXUtil();

		@Override
		public EventoInstanciaInativa coletarEvento() {

			Boolean isAtivo = false;
			try {
				isAtivo = (Boolean) instancia
						.invocarMetodoInstanciaExecutorConsulta(
								JMXUtil.MetodoInstancia.IS_ATIVO,
								super.getNomeElementoGerenciado("SENSOR_"));
			} catch (RuntimeException e) {
				isAtivo = false;
			}
			if (isAtivo == null || !isAtivo) {
				return new EventoInstanciaInativa(
						super.getNomeElementoGerenciado("SENSOR_"));
			}
			return null;
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new PublicarEventoInstanciaAtiva());
		super.addBehaviour(new PublicarEventoInstanciaInativa());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}