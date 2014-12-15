package asofth.prototype.agent.behaviour.impl;

import monitor.util.JMXQueueUtils;
import monitor.util.JMXQueueUtils.QueueMethod;
import jade.core.behaviours.OneShotBehaviour;

/**
 * Removes all the pending messages in the monitored JMS Queue
 */
public class QueueCleanerBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 8774518415742624077L;

	private JMXQueueUtils queueHelper = new JMXQueueUtils();

	@Override
	public void action() {

		try {
			// remove the pending messages
			this.queueHelper.executeMethodQueueViewMBean(QueueMethod.PURGE,
					null);

			super.myAgent.doDelete();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
