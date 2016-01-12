package org.registrator.community.service;

import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */
public interface ResourceDiscreteValueService {
    List<ResourceDiscreteValue> findByResource(Resource resource);

    List<ResourceDiscreteValue> findAllByDiscreteParameter (DiscreteParameter discreteParameter);

    List<ResourceDiscreteValue> findAllByValueAndDiscreteParameter (Double d, DiscreteParameter discreteParameter);
    List<ResourceDiscreteValue> findAllByBiggerValueAndDiscreteParameter (Double d, DiscreteParameter discreteParameter);
    List<ResourceDiscreteValue> findAllBySmallerValueAndDiscreteParameter (Double d, DiscreteParameter discreteParameter);
}
