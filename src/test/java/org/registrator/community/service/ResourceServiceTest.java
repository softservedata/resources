package org.registrator.community.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dto.ResourceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Assert;

/**
 * Unit test for Resource service methods
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
public class ResourceServiceTest {

    @Autowired
    ResourceService resourceService;
    
    @Test
    public void findResourcebyIdentifier() {
        
        ResourceDTO resourceDTO = resourceService.findByIdentifier("111111");
        Assert.assertEquals("111111",resourceDTO.getIdentifier());
        Assert.assertTrue(true);
    }

}
/*public class ResourceServiceTest {

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
        List<ResourceType> resourceTypesList = resourceTypeService.findAll();

        Assert.assertNull(resourceTypesList, "List of resource type is empty!");
    }

    
     * @Rule public TestWatcher watchman= new TestWatcher() {
     * 
     * @Override protected void failed(Throwable e, Description description) {
     * 
     * }
     * 
     * @Override protected void succeeded(Description description) {
     * 
     * } };
     
    @Test
    public final void deleteResourceTypeIfResourseDoesntExistTest() {

        Assert.assertEquals(true, true);

    }

    @Test
    public final void findByResourceTypeIdTest() {
        
         * ResourceType resourceType = resourceTypeService.findById(1);
         * Assert.assertNull(resourceType,
         * "ResourceType with such ID doesn't exist!");
         
    }

    @Test
    public final void findByResourceNameTest() {
        
         * ResourceType resourceType =
         * resourceTypeRepository.findByName("земельний");
         * Assert.assertNotNull(resourceType,
         * "Resource type with such name doesn't exist!");
         * Assert.assertEquals(resourceType, new ResourceType("земельний"));
         
    }

    @Test
    public final void addResourceTypeToDatabaseTest() {
    }
}

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
        List<ResourceType> resourceTypesList = resourceTypeService.findAll();
        
        Assert.assertNull(resourceTypesList, "List of resource type is empty!");
    }
    
    
    * @Rule public TestWatcher watchman= new TestWatcher() {
        * 
        * @Override protected void failed(Throwable e, Description description) {
            * 
            * }
        * 
        * @Override protected void succeeded(Description description) {
            * 
            * } };
            
            @Test
            public final void deleteResourceTypeIfResourseDoesntExistTest() {
                
                Assert.assertEquals(true, true);
                
            }
            
            @Test
            public final void findByResourceTypeIdTest() {
                
                * ResourceType resourceType = resourceTypeService.findById(1);
                * Assert.assertNull(resourceType,
                        * "ResourceType with such ID doesn't exist!");
                
            }
            
            @Test
            public final void findByResourceNameTest() {
                
                * ResourceType resourceType =
                        * resourceTypeRepository.findByName("земельний");
                * Assert.assertNotNull(resourceType,
                        * "Resource type with such name doesn't exist!");
                * Assert.assertEquals(resourceType, new ResourceType("земельний"));
                
            }
            
            @Test
            public final void addResourceTypeToDatabaseTest() {
            }
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
                    List<ResourceType> resourceTypesList = resourceTypeService.findAll();
                    
                    Assert.assertNull(resourceTypesList, "List of resource type is empty!");
                }
                
                
                * @Rule public TestWatcher watchman= new TestWatcher() {
                    * 
                    * @Override protected void failed(Throwable e, Description description) {
                        * 
                        * }
                    * 
                    * @Override protected void succeeded(Description description) {
                        * 
                        * } };
                        
                        @Test
                        public final void deleteResourceTypeIfResourseDoesntExistTest() {
                            
                            Assert.assertEquals(true, true);
                            
                        }
                        
                        @Test
                        public final void findByResourceTypeIdTest() {
                            
                            * ResourceType resourceType = resourceTypeService.findById(1);
                            * Assert.assertNull(resourceType,
                                    * "ResourceType with such ID doesn't exist!");
                            
                        }
                        
                        @Test
                        public final void findByResourceNameTest() {
                            
                            * ResourceType resourceType =
                                    * resourceTypeRepository.findByName("земельний");
                            * Assert.assertNotNull(resourceType,
                                    * "Resource type with such name doesn't exist!");
                            * Assert.assertEquals(resourceType, new ResourceType("земельний"));
                            
                        }
                        
                        @Test
                        public final void addResourceTypeToDatabaseTest() {
                        }
            }
*/