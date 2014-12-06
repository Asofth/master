package asofth.prototype.agent.behaviour.impl;

import asofth.prototype.event.QueueConsumerQuantityPrimitiveEvent;
import asofth.prototype.util.JMXQueueUtils;

public class QueueConsumerQuantityCollectorBehaviour extends
		AbstractCollectorBehaviour<QueueConsumerQuantityPrimitiveEvent> {

	private static final long serialVersionUID = -2853635510578899679L;

	private JMXQueueUtils queueHelper = new JMXQueueUtils();

	@Override
	public Long getIntervalInMilliseconds() {
		return 50L;
	}

	@Override
	public QueueConsumerQuantityPrimitiveEvent doCollect() {
		Long size = this.queueHelper.getProcessingQueueConsumerCount();

		if (size != null) {
			return new QueueConsumerQuantityPrimitiveEvent("CONSUMER_QUANTITY",
					this.queueHelper.getQueueName(), size);
		}
		return null;
	}

}
