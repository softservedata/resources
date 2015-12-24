package org.registrator.community.service.impl;

import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.service.ResourceLinearValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */
@Service
public class ResourceLinearValueServiceImpl implements ResourceLinearValueService {
    @Autowired
    private ResourceLinearValueRepository resourceLinearValueRepository;

    @Override
    public List<ResourceLinearValue> findAllByLinearParameter(LinearParameter linearParameter) {
        return resourceLinearValueRepository.findAllByLinearParameter(linearParameter);
    }

    @Override
    public List<ResourceLinearValue> findByResource(Resource resource) {
        return resourceLinearValueRepository.findByResource(resource);
    }

    @Override
    public List<ResourceLinearValue> findAllByValueAndLinearParameter(Double d, LinearParameter linearParameter) {
        return resourceLinearValueRepository.findByValueAndLinearParameterId(d, linearParameter);
    }
}
