package com.alerts.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.alerts.model.DispatchType;

public class DispatchStrategy {
	private DispatchType dispatchType;
	private String message;
	private String subject;

	@Autowired
	public AlertingService service;

	public DispatchStrategy(DispatchType dispatchType, String message, String subject) {
		this.dispatchType = dispatchType;
		this.message = message;
		this.subject = subject;
		this.service = new AlertingService();
	}

	public DispatchType getDispatchType() {
		return dispatchType;
	}

	public void setDispatchType(DispatchType dispatchType) {
		this.dispatchType = dispatchType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "DispatchStrategy [dispatchType=" + dispatchType + ", message=" + message + ", subject=" + subject + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dispatchType == null) ? 0 : dispatchType.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DispatchStrategy other = (DispatchStrategy) obj;
		if (dispatchType != other.dispatchType)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

}
