package asofth.prototype.event;

public class QueueSizePrimitiveEvent extends PrimitiveEvent {

	private String queueName;
	private long size;

	public QueueSizePrimitiveEvent(String id, String queueName, long size) {
		super(id);
		this.queueName = queueName;
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public String getQueueName() {
		return this.queueName;
	}

	@Override
	public String toString() {
		return super.getId() + "=" + this.size;
	}

}
