package asofth.prototype.agent.behaviour.impl;

import asofth.prototype.event.QueueConsumerQuantityPrimitiveEvent;
import asofth.prototype.util.JMXQueueUtils;
import asofth.prototype.util.JMXQueueUtils.QueueMethod;

public class QueueConsumerQuantityCollectorBehaviour extends
		AbstractCollectorBehaviour<QueueConsumerQuantityPrimitiveEvent> {

	private static final long serialVersionUID = -2853635510578899679L;

	private JMXQueueUtils queueHelper = new JMXQueueUtils();

	private long lastQuantity = 0;

	@Override
	public Long getIntervalInMilliseconds() {
		return 50L;
	}

	@Override
	public QueueConsumerQuantityPrimitiveEvent doCollect() {
		Long size = (Long) this.queueHelper.executeMethodQueueViewMBean(
				QueueMethod.CONSUMER_QUANTITY, null);

		if (size != null && this.lastQuantity != size) {
			QueueConsumerQuantityPrimitiveEvent event = new QueueConsumerQuantityPrimitiveEvent(
					"CONSUMER_QUANTITY", this.queueHelper.getQueueName(), size);
			this.lastQuantity = size;
			return event;
		}
		return null;
	}

}
