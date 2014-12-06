package asofth.prototype.agent.behaviour.impl;

import asofth.prototype.event.QueueSizePrimitiveEvent;
import asofth.prototype.util.JMXQueueUtils;

public class QueueSizeCollectorBehaviour extends
		AbstractCollectorBehaviour<QueueSizePrimitiveEvent> {

	private static final long serialVersionUID = -2853635510578899679L;

	private JMXQueueUtils queueHelper = new JMXQueueUtils();

	@Override
	public Long getIntervalInMilliseconds() {
		return 50L;
	}
	
	@Override
	public QueueSizePrimitiveEvent doCollect() {
		Long size = this.queueHelper.getProcessingQueueSize();

		if (size != null) {
			return new QueueSizePrimitiveEvent("QUEUE_SIZE",
					this.queueHelper.getQueueName(), size);
		}
		return null;
	}

}
