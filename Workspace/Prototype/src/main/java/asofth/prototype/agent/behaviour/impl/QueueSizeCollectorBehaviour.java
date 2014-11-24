package asofth.prototype.agent.behaviour.impl;

import asofth.prototype.event.QueueSizePrimitiveEvent;

public class QueueSizeCollectorBehaviour extends
		AbstractCollectorBehaviour<QueueSizePrimitiveEvent> {

	private static final long serialVersionUID = -2853635510578899679L;

	private JMXQueueHelper queueHelper = new JMXQueueHelper();

	@Override
	public Long getIntervalInMilliseconds() {
		return 100L;
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
