package com.binu.codingexcercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.binu.codingexcercise.constants.FeedbackMessages;
import com.binu.codingexcercise.dto.Customer;
import com.binu.codingexcercise.dto.ResponseDTO;
import com.binu.codingexcercise.service.PhoneDirectoryService;

@RestController
public class PhoneDirectoryRestController {

	@Autowired
	private FeedbackMessages feedbackMessages;

	@Autowired
	private PhoneDirectoryService phoneDirectoryService;

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public ResponseDTO addCustomer(@RequestBody Customer customerDTO) {
		ResponseDTO resp = new ResponseDTO();
		if (phoneDirectoryService.validateCustomer(customerDTO)) {
			if (phoneDirectoryService.validateNumber(customerDTO)) {
				resp = phoneDirectoryService.saveCustomer(customerDTO);
			} else {
				resp.setStatus(feedbackMessages.getFail());
				resp.setMsg(feedbackMessages.getNumberRequired());
			}
		} else {
			resp.setStatus(feedbackMessages.getFail());
			resp.setMsg(feedbackMessages.getNameRequired());
		}
		return resp;
	}

	@RequestMapping(value = "/activate/{number}")
	public ResponseDTO activatePhoneNumber(@PathVariable Long number) {
		ResponseDTO resp = phoneDirectoryService.activateNumber(number);
		return resp;
	}

	@RequestMapping(value = "/deactivate/{number}")
	public ResponseDTO deactivatePhoneNumber(@PathVariable Long number) {
		ResponseDTO resp = phoneDirectoryService.deactivateNumber(number);
		return resp;
	}

	@RequestMapping(value = "/getPhoneNumbers/{name}")
	public ResponseDTO getPhoneNumbers(@PathVariable String name) {

		ResponseDTO resp = phoneDirectoryService.getAllPhoneNumbers(name);
		return resp;
	}

	@RequestMapping(value = "/getPhoneNumbers")
	public ResponseDTO getAllPhoneNumbers() {
		ResponseDTO resp = phoneDirectoryService.getAllPhoneNumbers(null);
		return resp;
	}

}
