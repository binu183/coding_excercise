package com.binu.codingexcercise.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.binu.codingexcercise.AbstractTest;
import com.binu.codingexcercise.constants.FeedbackMessages;
import com.binu.codingexcercise.dto.Customer;
import com.binu.codingexcercise.dto.PhoneNumber;
import com.binu.codingexcercise.dto.ResponseDTO;

public class PhoneDirectoryRestControllerTest extends AbstractTest {

	@Value("${phonedirectory.defaultNumberType}")
	private String defaultType;

	@Autowired
	private FeedbackMessages feedbackMessages;

	@Value(value = "${server.port}")
	private String portNum;

	@Value(value = "${server.servlet.context-path}")
	private String serverContext;

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void addCustomer() throws Exception {
		Customer customer = populateCustomer1();
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(feedbackMessages.getSuccess());
		responseDTO.setMsg(feedbackMessages.getCustomerAdded());
		String url = "/addCustomer";
		String inputJson = super.mapToJson(customer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String responseJson = mvcResult.getResponse().getContentAsString();
		String exceptedJsonResponse = super.mapToJson(responseDTO);
		assertEquals(exceptedJsonResponse, responseJson);
	}

	@Test
	public void getPhoneNumber() throws Exception {
		List<Customer> exceptedResponseCustList = new ArrayList<Customer>();
		Customer customer = populateCustomer1();
		String url = "/addCustomer";
		exceptedResponseCustList.add(customer);
		String inputJson = super.mapToJson(customer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		customer = populateCustomer2();
		exceptedResponseCustList.add(customer);
		inputJson = super.mapToJson(customer);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		url = "/getPhoneNumbers";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		ResponseDTO responseDTO = new ResponseDTO(exceptedResponseCustList, feedbackMessages.getReturnallCustomer(),
				feedbackMessages.getSuccess());
		String exceptedResponseString = super.mapToJson(responseDTO);
		assertEquals(exceptedResponseString, responseString);
	}

	@Test
	public void getPhoneNumberByName() throws Exception {
		List<Customer> exceptedResponseCustList = new ArrayList<Customer>();
		Customer customer = populateCustomer1();
		String url = "/addCustomer";
		exceptedResponseCustList.add(customer);
		String inputJson = super.mapToJson(customer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		customer = populateCustomer2();
		inputJson = super.mapToJson(customer);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		url = "/getPhoneNumbers/Binu";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		ResponseDTO responseDTO = new ResponseDTO(exceptedResponseCustList, feedbackMessages.getCustomerFound(),
				feedbackMessages.getSuccess());
		String exceptedResponseString = super.mapToJson(responseDTO);
		assertEquals(exceptedResponseString, responseString);

		url = "/getPhoneNumbers/Someone";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		responseDTO = new ResponseDTO(new ArrayList<>(), feedbackMessages.getContactDetailsNotFound(),
				feedbackMessages.getFail());
		exceptedResponseString = super.mapToJson(responseDTO);
		assertEquals(exceptedResponseString, responseString);
	}

	@Test
	public void activateANumber() throws Exception {
		List<Customer> exceptedResponseCustList = new ArrayList<Customer>();
		Customer customer = populateCustomer1();
		String url = "/addCustomer";
		exceptedResponseCustList.add(customer);
		String inputJson = super.mapToJson(customer);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		String responseString = mvcResult.getResponse().getContentAsString();
		customer = populateCustomer2();
		inputJson = super.mapToJson(customer);
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		url = "/activate/9809320391";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		ResponseDTO responseDTO = new ResponseDTO(null, feedbackMessages.getActiavted(), feedbackMessages.getSuccess());
		String exceptedResponseString = super.mapToJson(responseDTO);
		assertEquals(exceptedResponseString, responseString);

		url = "/activate/9809320395";
		mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		responseString = mvcResult.getResponse().getContentAsString();
		responseDTO = new ResponseDTO(null, feedbackMessages.getNumberDontExist(), feedbackMessages.getFail());
		exceptedResponseString = super.mapToJson(responseDTO);
		assertEquals(exceptedResponseString, responseString);
	}

	private Customer populateCustomer1() {
		List<PhoneNumber> phoneNumList = new ArrayList<PhoneNumber>();
		PhoneNumber phoneNum = new PhoneNumber(null, null, defaultType, 9809320391L, false);
		phoneNumList.add(phoneNum);
		phoneNum = new PhoneNumber(null, null, defaultType, 9809320392L, false);
		phoneNumList.add(phoneNum);
		return new Customer(null, "Binu", "Varghese", phoneNumList);
	}

	private Customer populateCustomer2() {
		List<PhoneNumber> phoneNumList = new ArrayList<PhoneNumber>();
		PhoneNumber phoneNum = new PhoneNumber(null, null, defaultType, 9809320393L, false);
		phoneNumList.add(phoneNum);
		phoneNum = new PhoneNumber(null, null, defaultType, 9809320394L, false);
		phoneNumList.add(phoneNum);
		return new Customer(null, "Jake", "Varghese", phoneNumList);
	}

}
