package com.binu.codingexcercise.dto;

public class PhoneNumber {

	private Long id;

	private Long custId;

	private String type;

	private Long number;

	private boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public PhoneNumber(Long id, Long custId, String type, Long number, boolean isActive) {
		super();
		this.id = id;
		this.custId = custId;
		this.type = type;
		this.number = number;
		this.isActive = isActive;
	}

	public PhoneNumber() {

	}
}
