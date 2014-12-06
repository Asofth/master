package asofth.prototype.event;

import jade.util.leap.Serializable;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Abstract representation of a primitive event monitored
 */
public abstract class PrimitiveEvent implements Serializable {

	private static final long serialVersionUID = -347863980913150974L;

	private String id;
	private Date collectingDateTime;
	private String originAddress;

	public PrimitiveEvent(String id) {
		this.id = id;
		this.collectingDateTime = new Date();
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

	public Date getCollectingDateTime() {
		return collectingDateTime;
	}

}
