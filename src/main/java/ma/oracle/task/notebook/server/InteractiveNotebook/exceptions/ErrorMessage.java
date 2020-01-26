package ma.oracle.task.notebook.server.interactivenotebook.exceptions;

import java.util.Date;

public class ErrorMessage {

	private Date timestamp;
	private String mesage;

	public ErrorMessage() {
		super();
	}

	public ErrorMessage(Date timestamp, String mesage) {
		super();
		this.timestamp = timestamp;
		this.mesage = mesage;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMesage() {
		return mesage;
	}

	public void setMesage(String mesage) {
		this.mesage = mesage;
	}

}
