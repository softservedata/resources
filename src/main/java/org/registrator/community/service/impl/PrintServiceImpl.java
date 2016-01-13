package org.registrator.community.service.impl;

import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintServiceImpl implements PrintService{
	@Autowired
	InquiryRepository inquiryRepository;
	
	@Override
	public String printProcuration(Integer inquiryId){
		Inquiry inquiry = inquiryRepository.getOne(inquiryId);
		User user = inquiry.getUser();
		String userName = user.getLastName() + " " + user.getFirstName() + " " + user.getMiddleName();
		Address address = user.getAddress().get((user.getAddress().size()-1));
		String userAddress = address.getPostCode() + ", Україна, " + address.getCity() + 
				", вул." + address.getStreet() + " " + address.getBuilding() + "/" + address.getFlat(); 
		String date = inquiry.getDate().toString();
		User registrator = inquiry.getRegistrator();
		String registratorName = registrator.getLastName() + " " + registrator.getFirstName() + 
									" " + registrator.getMiddleName();
		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size()-1));
		String registratorAddress ="вул." + addressRegistrator.getStreet() + " " 
					+ addressRegistrator.getBuilding() + "/" + addressRegistrator.getFlat() + ", " 
					 + addressRegistrator.getCity() +", "+ addressRegistrator.getPostCode();
		String identifier = inquiry.getResource().getIdentifier();
		
		String print = "print";
		
		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)){
			print = printOutputProcuration(userName, userAddress, date, registratorName, 
					registratorAddress, identifier);
		}
		
		return print;
	}
	
	public String printOutputProcuration(String userName, String userAddress, String date,
				String registratorName, String registratorAddress, String identifier){
		
		String print = userName + ",       "+ userAddress + ",       " + date + ",       " 
		+ registratorName + ",       " + registratorAddress + ",       " + identifier;
		System.out.println("!!!!!" + print);
		return print;
	}
}
