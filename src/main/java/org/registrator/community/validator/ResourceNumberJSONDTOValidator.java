package org.registrator.community.validator;

import java.util.List;

import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResourceNumberJSONDTOValidator implements Validator {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ResourceNumberRepository resourceNumberRepository;

	@Autowired
	TomeRepository tomeRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(ResourceNumberDTOJSON.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		ResourceNumberDTOJSON resourceNumberDTOJSON = (ResourceNumberDTOJSON) object;
		User user = userRepository.findUserByLogin(resourceNumberDTOJSON.getLogin());
		ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
		Tome tome = tomeRepository.findTomeByRegistrator(user);
		List<ResourceNumber> registratorNumberList = resourceNumberRepository.findAll();
		List<Tome> tomeList = tomeRepository.findAll();
		if (resourceNumber != null) {
			errors.rejectValue("registrator_number", "За цим користувачем уже закрілений номер");
		}

		for (ResourceNumber registratorNumber : registratorNumberList) {
			if (registratorNumber.getRegistratorNumber().equals(resourceNumberDTOJSON.getRegistrator_number())) {
				errors.rejectValue("registrator_number", "Реєстраційний номер є унікальний");
			}
		}
		
		if (tome != null) {
			errors.rejectValue("identifier", "За цим користувачем уже закрілений номер");
		}
		
		for (Tome tomeNumber : tomeList) {
			if (resourceNumberDTOJSON.getIdentifier().equals(tomeNumber.getIdentifier())) {
				errors.rejectValue("identifier", "Реєстраційний номер є унікальний");
			}
		}
	}

}
