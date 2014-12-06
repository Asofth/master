package asofth.prototype.event;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jade.util.leap.Serializable;

/**
 * Abstract representation of a primitive event monitored
 */
public abstract class PrimitiveEvent implements Serializable {

	private static final long serialVersionUID = -347863980913150974L;

	private String id;
	private String originAddress;

	public PrimitiveEvent(String id) {
		this.id = id;
		try {
			this.originAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			this.originAddress = null;
		}
	}

	public String getId() {
		return this.id;
	}

	public String getOriginAddress() {
		return originAddress;
	}

}
