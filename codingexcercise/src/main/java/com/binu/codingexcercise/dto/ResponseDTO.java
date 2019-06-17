package com.binu.codingexcercise.dto;

import java.util.List;

public class ResponseDTO {

	private List<Customer> customerList;

	private String msg;

	private String status;

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseDTO(List<Customer> customerList, String msg, String status) {
		super();
		this.customerList = customerList;
		this.msg = msg;
		this.status = status;
	}

	public ResponseDTO() {

	}

}
