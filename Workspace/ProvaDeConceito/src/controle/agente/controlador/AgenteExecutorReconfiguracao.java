package controle.agente.controlador;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.kie.internal.runtime.StatefulKnowledgeSession;

import util.DFUtil;
import util.Log;

public class AgenteExecutorReconfiguracao extends Agent {

	private static final long serialVersionUID = 273786892468632402L;

	public static final long SESSION_ID = UUID.randomUUID()
			.getLeastSignificantBits();

	private StatefulKnowledgeSession session = null;

	public class Reconfiguracao {

		private Class<? extends Agent> classeAgente;
		private String mensagem;

		public Reconfiguracao(Class<? extends Agent> classeAgente,
				String mensagem) {
			this.classeAgente = classeAgente;
			this.mensagem = mensagem;
		}

		@Override
		public boolean equals(Object obj) {
			Reconfiguracao other = (Reconfiguracao) obj;
			return this.classeAgente.equals(other.classeAgente)
					&& this.mensagem.equals(other.mensagem);
		}

		@Override
		public int hashCode() {
			int hash = 1;
			hash = hash * 17 + classeAgente.hashCode();
			hash = hash * 31 + classeAgente.hashCode();
			return hash;
		}

	}

	private Queue<Reconfiguracao> reconfiguracoes = new ConcurrentLinkedQueue<>();

	public void ruleFired(Class<? extends Agent> classeAgente, String mensagem) {
		Reconfiguracao r = new Reconfiguracao(classeAgente, mensagem);
		if (!reconfiguracoes.contains(r)) {
			reconfiguracoes.add(r);
		}
	}

	public class ExecutorReconfiguracao extends CyclicBehaviour {

		private static final long serialVersionUID = -1017720562038855599L;

		@Override
		public void action() {
			try {
				Reconfiguracao reconfiguracao = ((AgenteExecutorReconfiguracao) this.myAgent).reconfiguracoes
						.poll();
				if (reconfiguracao != null) {
					System.out.println(">> "+((AgenteExecutorReconfiguracao) this.myAgent).reconfiguracoes.size());
					((AgenteExecutorReconfiguracao) this.myAgent).reconfiguracoes
							.size();
					ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
					msg.setContent(reconfiguracao.mensagem);
					msg.addReceiver(DFUtil.search(this.myAgent,
							reconfiguracao.classeAgente));
					this.myAgent.send(msg);
					Thread.sleep(300);
				}
			} catch (Exception e) {
				Log.registrar(e);
			}
		}
	}

	@Override
	protected void setup() {
		super.setup();
		this.session = (StatefulKnowledgeSession) super.getArguments()[0];
		super.addBehaviour(new ExecutorReconfiguracao());

		this.session.setGlobal("controladorAtuacao", this);

		DFUtil.register(this);
	}

	@Override
	protected void finalize() throws Throwable {

		DFUtil.deregister(this);
		super.finalize();
	}

}
