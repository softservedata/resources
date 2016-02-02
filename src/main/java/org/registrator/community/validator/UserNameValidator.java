package org.registrator.community.validator;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.UserNameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserNameValidator implements Validator {

    @Autowired
    public UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ResourceTypeDTO.class);
    }

    /**
     * Validate user login
     */
    @Override
    public void validate(Object target, Errors errors) {
        UserNameDTO userNameDTO = (UserNameDTO) target;

        if (userRepository.findUserByLogin(userNameDTO.getLogin()) != null) {
            errors.rejectValue("login", "msg.resourcetype.typename.exist");
        }
    }
}
