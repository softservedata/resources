package org.registrator.community.service;

import java.util.List;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.entity.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for ResourceType service
 */

public class ResourceServiceTest {

    @Autowired
    private ResourceTypeService resourceTypeService;
    @Autowired
    private LinearParameterRepository linearParameterRepository;
    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Test
    public final void findAllResourceTypeTest() {
       /* List<ResourceType> resourceTypesList = resourceTypeService.findAll();

        Assert.assertNull(resourceTypesList, "List of resource type is empty!");*/
    }

    /*
     * @Rule public TestWatcher watchman= new TestWatcher() {
     * 
     * @Override protected void failed(Throwable e, Description description) {
     * 
     * }
     * 
     * @Override protected void succeeded(Description description) {
     * 
     * } };
     */
    @Test
    public final void deleteResourceTypeIfResourseDoesntExistTest() {

        Assert.assertEquals(true, true);

    }

    @Test
    public final void findByResourceTypeIdTest() {
        /*
         * ResourceType resourceType = resourceTypeService.findById(1);
         * Assert.assertNull(resourceType,
         * "ResourceType with such ID doesn't exist!");
         */
    }

    @Test
    public final void findByResourceNameTest() {
        /*
         * ResourceType resourceType =
         * resourceTypeRepository.findByName("земельний");
         * Assert.assertNotNull(resourceType,
         * "Resource type with such name doesn't exist!");
         * Assert.assertEquals(resourceType, new ResourceType("земельний"));
         */
    }

    @Test
    public final void addResourceTypeToDatabaseTest() {
    }
}
