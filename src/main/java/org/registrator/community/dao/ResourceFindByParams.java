package org.registrator.community.dao;

import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.entity.Resource;

import java.util.List;

/**
 * Provides resource search by parameters. This interface needed to create additional behavior for ResourceRepository
 * which is standard JPARepository
 */
public interface ResourceFindByParams {
    /**
     * Search of resources by parameters. All parameters comparisons are combined with AND logical operation
     * @param parameters resource parameters and values for searching
     * @return List fo found resources
     */
    List<Resource> findByParams(ResourseSearchJson parameters);
}
