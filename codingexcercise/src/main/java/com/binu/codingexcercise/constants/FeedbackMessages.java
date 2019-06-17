
package com.binu.codingexcercise.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "phonedirectory.feedback")
public class FeedbackMessages {

	private String success;

	private String fail;

	private String found;

	private String notFound;

	private String numberDontExist;

	private String actiavted;

	private String deactivated;

	private String contactDetailsNotFound;

	private String customerFound;

	private String returnallCustomer;

	private String nameRequired;

	private String numberRequired;

	private String customerAdded;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public String getNotFound() {
		return notFound;
	}

	public void setNotFound(String notFound) {
		this.notFound = notFound;
	}

	public String getNumberDontExist() {
		return numberDontExist;
	}

	public void setNumberDontExist(String numberDontExist) {
		this.numberDontExist = numberDontExist;
	}

	public String getActiavted() {
		return actiavted;
	}

	public void setActiavted(String actiavted) {
		this.actiavted = actiavted;
	}

	public String getDeactivated() {
		return deactivated;
	}

	public void setDeactivated(String deactivated) {
		this.deactivated = deactivated;
	}

	public String getContactDetailsNotFound() {
		return contactDetailsNotFound;
	}

	public void setContactDetailsNotFound(String contactDetailsNotFound) {
		this.contactDetailsNotFound = contactDetailsNotFound;
	}

	public String getCustomerFound() {
		return customerFound;
	}

	public void setCustomerFound(String customerFound) {
		this.customerFound = customerFound;
	}

	public String getReturnallCustomer() {
		return returnallCustomer;
	}

	public void setReturnallCustomer(String returnallCustomer) {
		this.returnallCustomer = returnallCustomer;
	}

	public String getNameRequired() {
		return nameRequired;
	}

	public void setNameRequired(String nameRequired) {
		this.nameRequired = nameRequired;
	}

	public String getNumberRequired() {
		return numberRequired;
	}

	public void setNumberRequired(String numberRequired) {
		this.numberRequired = numberRequired;
	}

	public String getCustomerAdded() {
		return customerAdded;
	}

	public void setCustomerAdded(String customerAdded) {
		this.customerAdded = customerAdded;
	}

}
