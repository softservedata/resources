package org.registrator.community.validator;
import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.entity.TerritorialCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CommunityValidator implements Validator {

    @Autowired
    CommunityRepository communityRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TerritorialCommunity.class);
    }

    /**
     * Validate community name
     */
    @Override
    public void validate(Object target, Errors errors) {

        TerritorialCommunity territorialCommunity = (TerritorialCommunity) target;
        if (communityRepository.findByName(territorialCommunity.getName()) != null) {
            errors.rejectValue("name", "msg.resourcetype.typename.exist");
        }
    }
}