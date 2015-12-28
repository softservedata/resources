package org.registrator.community.service.impl;

import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.service.ResourceDiscreteValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */
@Service
public class ResourceDiscreteValueServiceImpl implements ResourceDiscreteValueService {
    @Autowired
    private ResourceDiscreteValueRepository resourceDiscreteValueRepository;

    @Override
    public List<ResourceDiscreteValue> findByResource(Resource resource) {
        return resourceDiscreteValueRepository.findByResource(resource);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByDiscreteParameter(DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByDiscreteParameter(discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByValueAndDiscreteParameter(d, discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllBySmallerValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllBySmallerValueAndDiscreteParameter(d, discreteParameter);
    }

    @Override
    public List<ResourceDiscreteValue> findAllByBiggerValueAndDiscreteParameter(Double d, DiscreteParameter discreteParameter) {
        return resourceDiscreteValueRepository.findAllByBiggerValueAndDiscreteParameter(d, discreteParameter);
    }
}
