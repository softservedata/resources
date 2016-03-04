package org.registrator.community.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.entity.Resource;
import org.slf4j.Logger;
import org.registrator.community.service.impl.ResourceDeleteServiceImpl;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceDeleteServiceTest {

    private static final String ID = "id";

    @InjectMocks
    private ResourceDeleteService resourceDeleteServiceImpl = new ResourceDeleteServiceImpl();

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private Logger logger;

    @Test
    public void testDeleteResourceRetrieveResourceByCorrectIdentifier() throws Exception {
        resourceDeleteServiceImpl.deleteResource(ID);
        verify(resourceRepository).findByIdentifier(ID);
    }

    @Test
    public void testDeleteResourceDeleteCorrectResource() throws Exception {
        Resource persistedResource = new Resource();
        when(resourceRepository.findByIdentifier(ID)).thenReturn(persistedResource);
        resourceDeleteServiceImpl.deleteResource(ID);
        verify(resourceRepository).delete(persistedResource);
    }
}
