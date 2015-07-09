package controle.agente.sensor;

import jade.core.Agent;
import util.Ambiente;
import util.DFUtil;
import controle.agente.sensor.comportamento.PublicarEventoPrimitivo;
import controle.evento.EventoInstanciaAtiva;
import controle.evento.EventoInstanciaInativa;

public class AgenteSensorInstancia extends Agent {

	private static final long serialVersionUID = 4585767475494660603L;

	public class InstanciaAtiva extends
			PublicarEventoPrimitivo<EventoInstanciaAtiva> {

		private static final long serialVersionUID = -3446027377283448304L;

		/**
		 * TODO: implementar a geração do evento a partir do estado da instância
		 */
		@Override
		public EventoInstanciaAtiva coletarEvento() {
			return new EventoInstanciaAtiva("ID", Ambiente.getNomeInstancia());
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	public class InstanciaInativa extends
			PublicarEventoPrimitivo<EventoInstanciaInativa> {

		private static final long serialVersionUID = 2564307284783450889L;

		/**
		 * TODO: implementar a geração do evento a partir do estado da instância
		 */
		@Override
		public EventoInstanciaInativa coletarEvento() {
			return new EventoInstanciaInativa("ID", Ambiente.getNomeInstancia());
		}

		@Override
		public long getIntervaloExecucaoMilisegundos() {
			return 1000;
		}
	}

	@Override
	protected void setup() {
		super.setup();
		super.addBehaviour(new InstanciaAtiva());
		super.addBehaviour(new InstanciaInativa());

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}