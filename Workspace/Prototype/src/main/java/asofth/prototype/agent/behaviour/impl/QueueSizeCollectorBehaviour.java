package asofth.prototype.agent.behaviour.impl;

import monitor.util.JMXQueueUtils;
import monitor.util.JMXQueueUtils.QueueMethod;
import asofth.prototype.event.QueueSizePrimitiveEvent;

public class QueueSizeCollectorBehaviour extends
		AbstractCollectorBehaviour<QueueSizePrimitiveEvent> {

	private static final long serialVersionUID = -2853635510578899679L;

	private JMXQueueUtils queueHelper = new JMXQueueUtils();

	private long lastQuantity = 0;

	@Override
	public Long getIntervalInMilliseconds() {
		return 50L;
	}

	@Override
	public QueueSizePrimitiveEvent doCollect() {
		Long size = (Long) this.queueHelper.executeMethodQueueViewMBean(
				QueueMethod.QUEUE_SIZE, null);

		if (size != null && this.lastQuantity != size) {
			QueueSizePrimitiveEvent event = new QueueSizePrimitiveEvent(
					"QUEUE_SIZE", this.queueHelper.getQueueName(), size);
			this.lastQuantity = size;
			return event;
		}
		return null;
	}
}
