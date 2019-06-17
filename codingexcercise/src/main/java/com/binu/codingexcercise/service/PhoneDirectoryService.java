package com.binu.codingexcercise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.binu.codingexcercise.constants.FeedbackMessages;
import com.binu.codingexcercise.dto.Customer;
import com.binu.codingexcercise.dto.PhoneNumber;
import com.binu.codingexcercise.dto.ResponseDTO;

@Service
public class PhoneDirectoryService {

	@Autowired
	private FeedbackMessages feedbackMessages;

	@Value("${phonedirectory.defaultNumberType}")
	private String defaultType;

	private Map<String, Customer> customerMap;

	private Map<String, List<String>> firstNameFullNameMap;

	private Map<String, List<String>> lastNameFullNameMap;

	private Map<Long, String> numberCustNameMap;

	private PhoneDirectoryService() {
		customerMap = new HashMap<String, Customer>();
		firstNameFullNameMap = new HashMap<String, List<String>>();
		lastNameFullNameMap = new HashMap<String, List<String>>();
		numberCustNameMap = new HashMap<Long, String>();
	}

	public boolean validateCustomer(Customer customerDTO) {
		boolean retFlag = true;
		if (customerDTO.getFirstName() == null || customerDTO.getFirstName().isEmpty()) {
			retFlag = false;
		}
		if (customerDTO.getLastName() == null || customerDTO.getLastName().isEmpty()) {
			retFlag = false;
		}
		return retFlag;
	}

	public boolean validateNumber(Customer customerDTO) {
		boolean retFlag = true;
		if (CollectionUtils.isEmpty(customerDTO.getPhoneNumbers())) {
			retFlag = false;
		} else {
			for (PhoneNumber phoneNum : customerDTO.getPhoneNumbers()) {
				if (phoneNum.getNumber() == null || phoneNum.getNumber().equals(0L)) {
					retFlag = false;
				}
			}
		}
		return retFlag;
	}

	public ResponseDTO activateNumber(Long number) {
		ResponseDTO resp = new ResponseDTO();
		String custName = numberCustNameMap.get(number);
		if (custName != null) {
			Customer customerDTO = customerMap.get(custName);
			List<PhoneNumber> phoneDTOList = customerDTO.getPhoneNumbers();
			if (CollectionUtils.isNotEmpty(phoneDTOList)) {
				boolean isFound = false;
				for (PhoneNumber phnNum : phoneDTOList) {
					if (phnNum.getNumber().equals(number)) {
						phnNum.setActive(true);
						isFound = true;
						break;
					}
				}
				if (isFound) {
					resp.setMsg(feedbackMessages.getActiavted());
					resp.setStatus(feedbackMessages.getSuccess());
				} else {
					resp.setMsg(feedbackMessages.getNumberDontExist());
					resp.setStatus(feedbackMessages.getFail());
				}
				customerMap.put(custName, customerDTO);
			}
		} else {
			resp.setMsg(feedbackMessages.getNumberDontExist());
			resp.setStatus(feedbackMessages.getFail());
		}
		return resp;
	}

	public ResponseDTO deactivateNumber(Long number) {
		ResponseDTO resp = new ResponseDTO();
		String custName = numberCustNameMap.get(number);
		if (custName != null) {
			Customer customerDTO = customerMap.get(custName);
			List<PhoneNumber> phoneDTOList = customerDTO.getPhoneNumbers();
			if (CollectionUtils.isNotEmpty(phoneDTOList)) {
				boolean isFound = false;
				for (PhoneNumber phnNum : phoneDTOList) {
					if (phnNum.getNumber().equals(number)) {
						phnNum.setActive(false);
						isFound = true;
						break;
					}
				}
				if (isFound) {
					resp.setMsg(feedbackMessages.getDeactivated());
					resp.setStatus(feedbackMessages.getSuccess());
				} else {
					resp.setMsg(feedbackMessages.getNumberDontExist());
					resp.setStatus(feedbackMessages.getFail());
				}
				customerMap.put(custName, customerDTO);
			}
		} else {
			resp.setMsg(feedbackMessages.getNumberDontExist());
			resp.setStatus(feedbackMessages.getFail());
		}
		return resp;
	}

	public ResponseDTO getAllPhoneNumbers(String name) {
		ResponseDTO resp = new ResponseDTO();
		List<Customer> custList = new ArrayList<Customer>();
		if (name == null) {
			customerMap.entrySet().stream().forEach(entryObj -> custList.add(entryObj.getValue()));
			resp.setMsg(feedbackMessages.getReturnallCustomer());
			resp.setStatus(feedbackMessages.getSuccess());
		} else {
			if (customerMap.get(name) != null) {
				custList.add(customerMap.get(name));
				resp.setMsg(feedbackMessages.getCustomerFound());
				resp.setStatus(feedbackMessages.getSuccess());
			} else {
				boolean isFound = false;
				Set<String> fullNameList = new HashSet<String>();
				if (firstNameFullNameMap.get(name) != null) {
					isFound = true;
					fullNameList.addAll(firstNameFullNameMap.get(name));
				}
				if (lastNameFullNameMap.get(name) != null) {
					isFound = true;
					fullNameList.addAll(lastNameFullNameMap.get(name));
				}
				if (isFound) {
					for (String fullName : fullNameList) {
						custList.add(customerMap.get(fullName));
					}
					resp.setMsg(feedbackMessages.getCustomerFound());
					resp.setStatus(feedbackMessages.getSuccess());
				} else {
					resp.setMsg(feedbackMessages.getContactDetailsNotFound());
					resp.setStatus(feedbackMessages.getFail());
				}
			}

		}
		resp.setCustomerList(custList);
		return resp;
	}

	public ResponseDTO saveCustomer(Customer customerDTO) {
		ResponseDTO resp = new ResponseDTO();
		Customer existingCustomer = customerMap.get(customerDTO.getFirstName() + customerDTO.getLastName());
		List<PhoneNumber> phoneNumList = customerDTO.getPhoneNumbers();
		populateNumberTypes(phoneNumList);
		if (existingCustomer != null) {
			List<PhoneNumber> existingPhoneNumList = existingCustomer.getPhoneNumbers();
			for (PhoneNumber phoneNumDTO : phoneNumList) {
				if (checkIfUnique(phoneNumDTO, existingPhoneNumList)) {
					existingPhoneNumList.add(phoneNumDTO);
					numberCustNameMap.put(phoneNumDTO.getNumber(),
							customerDTO.getFirstName() + customerDTO.getLastName());
				}

			}
			customerDTO.setPhoneNumbers(existingPhoneNumList);
		} else {
			populateSearchSupportingMaps(customerDTO);
		}
		customerMap.put(customerDTO.getFirstName() + customerDTO.getLastName(), customerDTO);

		resp.setStatus(feedbackMessages.getSuccess());
		resp.setMsg(feedbackMessages.getCustomerAdded());
		return resp;
	}

	private boolean checkIfUnique(PhoneNumber phoneNumDTO, List<PhoneNumber> existingPhoneNumList) {
		boolean isUnique = true;
		for (PhoneNumber phnNum : existingPhoneNumList) {
			if (phoneNumDTO.getNumber().equals(phnNum.getNumber())) {
				isUnique = false;
			}
		}
		return isUnique;
	}

	private void populateNumberTypes(List<PhoneNumber> phoneNumList) {
		phoneNumList.stream().forEach(new Consumer<PhoneNumber>() {

			@Override
			public void accept(PhoneNumber t) {
				if (t.getType() == null) {
					t.setType(defaultType);
				}

			}
		});
	}

	private void populateSearchSupportingMaps(Customer customerDTO) {
		List<String> fullNameList = null;
		if (firstNameFullNameMap.get(customerDTO.getFirstName()) == null) {
			fullNameList = new ArrayList<String>();
		} else {
			fullNameList = firstNameFullNameMap.get(customerDTO.getFirstName());
		}
		fullNameList.add(customerDTO.getFirstName() + customerDTO.getLastName());
		firstNameFullNameMap.put(customerDTO.getFirstName(), fullNameList);
		if (lastNameFullNameMap.get(customerDTO.getLastName()) == null) {
			fullNameList = new ArrayList<String>();
		} else {
			fullNameList = lastNameFullNameMap.get(customerDTO.getLastName());
		}
		fullNameList.add(customerDTO.getFirstName() + customerDTO.getLastName());
		lastNameFullNameMap.put(customerDTO.getLastName(), fullNameList);
		for (PhoneNumber num : customerDTO.getPhoneNumbers()) {
			numberCustNameMap.put(num.getNumber(), customerDTO.getFirstName() + customerDTO.getLastName());
		}
	}

}
