//package org.registrator.community.validator;
//
//import org.registrator.community.dao.ResourceNumberRepository;
//import org.registrator.community.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//@Component
//public class UserValidator implements Validator {
//
//	@Autowired
//	ResourceNumberRepository resourceNumberRepository;
//	
//	@Override
//	public boolean supports(Class<?> clazz) {
//		return clazz.equals(User.class);
//	}
//
//	@Override
//	public void validate(Object target, Errors errors) {
//		User user = (User) target;
//		Integer id = user.getUserId();
//		if(resourceNumberRepository.findById(id)!=null) {
//			errors.rejectValue("registrator_number", "Юзер вже існує в базі");
//		}
//	}
//
//}
