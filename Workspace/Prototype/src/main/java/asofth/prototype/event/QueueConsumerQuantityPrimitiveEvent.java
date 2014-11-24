package asofth.prototype.event;

public class QueueConsumerQuantityPrimitiveEvent extends PrimitiveEvent {

	private String queueName;
	private long consumerQuantity;

	public QueueConsumerQuantityPrimitiveEvent(String id, String queueName,
			long consumerQuantity) {
		super(id);
		this.queueName = queueName;
		this.consumerQuantity = consumerQuantity;
	}

	public long getConsumerQuantity() {
		return this.consumerQuantity;
	}

	public String getQueueName() {
		return this.queueName;
	}

	@Override
	public String toString() {
		return super.getId() + "=" + this.consumerQuantity;
	}

}
