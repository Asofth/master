package asofth.prototype.event;

public abstract class PrimitiveEvent {

	private String id;

	public PrimitiveEvent(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

}
