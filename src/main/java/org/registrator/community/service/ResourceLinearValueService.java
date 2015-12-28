package org.registrator.community.service;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceLinearValue;

import java.util.List;

/**
 * Created by Oleksiy on 24.12.2015.
 */
public interface ResourceLinearValueService {
    List<ResourceLinearValue> findByResource(Resource resource);

    List<ResourceLinearValue> findAllByLinearParameter (LinearParameter linearParameter);

    List<ResourceLinearValue> findAllByValueAndLinearParameter (Double d, LinearParameter linearParameter);
}
