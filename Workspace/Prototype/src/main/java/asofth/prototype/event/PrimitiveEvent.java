package asofth.prototype.event;

import jade.util.leap.Serializable;

/**
 * Abstract representation of a primitive event monitored
 */
public abstract class PrimitiveEvent implements Serializable {

	private static final long serialVersionUID = -347863980913150974L;

	private String id;

	public PrimitiveEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

}
