package org.registrator.community.service.incasetests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.TypeParameterDTO;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceTypeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ActiveProfiles("testing")
@ContextConfiguration(classes = { TestingConfiguration.class, LoggingConfig.class, SpringRootConfig.class })
public class ResourceTypeServiceImplTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private Logger logger;
	@Autowired
	private ResourceRepository resRep;
	@Autowired
	private ResourceTypeRepository resTypeRep;
	@Autowired
	private ResourceTypeService resTypeServ;
	@Autowired
	private LinearParameterRepository linearParameterRepository;
	@Autowired
	private DiscreteParameterRepository discreteParameterRepository;
	@Autowired
	private UserRepository userRep;
	@Autowired
	private TomeRepository tomeRep;

	private int desiredResources = 10, resTypeIdent = 0;
	private List<ResourceType> cResTypeList = new ArrayList<ResourceType>();
	private List<ResourceTypeDTO> cResTypeDTOList = new ArrayList<ResourceTypeDTO>();

	// DataProvider
	@DataProvider(name = "DataForResourceTypes")
	public Object[][] formDataForResourceTypes() {
		Object[][] tmp = new Object[desiredResources][];
		String resIdentMask = "resourceType#%03d";

		for (int i = 0; i < tmp.length; i++) {
			ResourceType res = new ResourceType();

			res.setTypeName(String.format(resIdentMask, resTypeIdent++));

			List<DiscreteParameter> discList = new ArrayList<DiscreteParameter>(3);
			List<LinearParameter> linList = new ArrayList<LinearParameter>(3);
			for (int j = 0; j < 6; j++) {
				if (j % 2 == 0) {
					DiscreteParameter param = new DiscreteParameter();
					param.setUnitName("param#" + (i * 10 + j));
					param.setDescription("desc");
					discList.add(param);
				} else {
					LinearParameter param = new LinearParameter();
					param.setUnitName("param#" + (i * 10 + j));
					param.setDescription("desc");
					linList.add(param);
				}
			}
			res.setDiscreteParameters(discList);
			res.setLinearParameters(linList);

			tmp[i] = new Object[] { res };
		}

		return tmp;
	}

	@DataProvider(name = "DataForSearchOps")
	public Object[][] formDataForSearch() {
		Object[][] tmp = new Object[cResTypeList.size()][1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new Object[] { cResTypeList.get(i) };
		}
		return tmp;
	}

	@DataProvider(name = "DataForDTOEdit")
	public Object[][] formDataForDTOEditOp() {
		Object[][] tmp = new Object[cResTypeDTOList.size()][1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new Object[] { cResTypeDTOList.get(i) };
		}
		return tmp;
	}

	@DataProvider(name = "PrepareDataForDeletion")
	public Object[][] prepareDataForDeletion() {
		Object[][] tmp = new Object[cResTypeList.size()][];

		Date date = new Date();
		for (int i = 0; i < tmp.length; i++) {
			ResourceType resType = cResTypeList.get(i);
			Resource res = new Resource();
			res.setDate(date);
			res.setDescription("desc");
			res.setIdentifier(resType.getTypeName() + ".resource#" + i);
			res.setReasonInclusion("reason");
			res.setType(resType);
			res.setRegistrator(userRep.findUserByLogin("registrator"));
			res.setStatus(ResourceStatus.ACTIVE);
			res.setTome(tomeRep.findOne(1));

			resRep.save(res);
			
			tmp[i] = new Object[]{resType};
		}

		return tmp;
	}

	// Tests

	@Test(dataProvider = "DataForResourceTypes", priority=1)
	public void addResourceType(ResourceType expected) {
		long size = resTypeRep.count();

		ResourceType actual = resTypeServ.addResourceType(expected);
		actual = resTypeRep.findByName(actual.getTypeName());

		AssertJUnit.assertNotNull(actual);

		AssertJUnit.assertEquals(expected.getTypeName(), actual.getTypeName());
		AssertJUnit.assertEquals(expected.getTypeId(), actual.getTypeId());
		AssertJUnit.assertEquals((size + 1), resTypeRep.count());

		cResTypeList.add(actual);
	}

	@Test(dataProvider = "DataForResourceTypes", priority=2)
	public void addResourceTypeDTO(ResourceType res) {
		ResourceTypeDTO expected = new ResourceTypeDTO();
		expected.setTypeName(res.getTypeName());

		List<TypeParameterDTO> typeParList = new ArrayList<TypeParameterDTO>();
		for (LinearParameter param : res.getLinearParameters()) {
			TypeParameterDTO dtoPar = new TypeParameterDTO();
			dtoPar.setParametersType("linearParameters");
			dtoPar.setDescription(param.getDescription());
			dtoPar.setUnitName(param.getUnitName());

			typeParList.add(dtoPar);
		}

		for (DiscreteParameter param : res.getDiscreteParameters()) {
			TypeParameterDTO dtoPar = new TypeParameterDTO();
			dtoPar.setParametersType("discreteParameters");
			dtoPar.setDescription(param.getDescription());
			dtoPar.setUnitName(param.getUnitName());

			typeParList.add(dtoPar);
		}
		expected.setParameters(typeParList);

		ResourceTypeDTO actual = resTypeServ.addResourceTypeDTO(expected);

		ResourceType fromActual = resTypeRep.findByName(actual.getTypeName());
		AssertJUnit.assertNotNull(fromActual);

		AssertJUnit.assertEquals(actual.getTypeName(), expected.getTypeName());

		List<TypeParameterDTO> actualParamList = actual.getParameters(), expectedParamList = expected.getParameters();
		AssertJUnit.assertEquals(actualParamList.size(), expectedParamList.size());

		for (int i = 0; i < actualParamList.size(); i++) {
			TypeParameterDTO arg0 = actualParamList.get(i), arg1 = expectedParamList.get(i);

			AssertJUnit.assertEquals(arg0.getParametersType(), arg1.getParametersType());
			AssertJUnit.assertEquals(arg0.getDescription(), arg1.getDescription());
			AssertJUnit.assertEquals(arg0.getUnitName(), arg1.getUnitName());
		}

		cResTypeList.add(fromActual);
		cResTypeDTOList.add(actual);
	}

	@Test(dataProvider = "DataForSearchOps", priority=3)
	public void findById(ResourceType expected) {
		ResourceType actual = resTypeServ.findById(expected.getTypeId());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
	}

	@Test(dataProvider = "DataForSearchOps", priority=4)
	public void findByName(ResourceType expected) {
		ResourceType actual = resTypeServ.findByName(expected.getTypeName());

		Assert.assertNotNull(actual);
		Assert.assertEquals(expected.getTypeName(), actual.getTypeName());
		Assert.assertEquals(expected.getTypeId(), actual.getTypeId());
	}

	@Test(priority=5)
	public void findAll() {
		List<ResourceType> resTypeList = resTypeServ.findAll();
		Assert.assertEquals(resTypeList.size(), cResTypeList.size() + 1);
	}

	@Test(dataProvider = "DataForDTOEdit", priority=6)
	public void editResourceType(ResourceTypeDTO expected) {
		ResourceTypeDTO forActual = resTypeServ.editResourceType(expected);
		ResourceType actual = resTypeRep.findByName(forActual.getTypeName());
		Assert.assertNotNull(actual);

		Assert.assertEquals(actual.getTypeName(), expected.getTypeName());

		List<DiscreteParameter> actualDisParams = discreteParameterRepository.findAllByResourceType(actual);
		List<LinearParameter> actualLinParams = linearParameterRepository.findByResourceType(actual);

		List<TypeParameterDTO> typeParList = new ArrayList<TypeParameterDTO>();
		for (LinearParameter param : actualLinParams) {
			TypeParameterDTO dtoPar = new TypeParameterDTO();
			dtoPar.setParametersType("linearParameters");
			dtoPar.setDescription(param.getDescription());
			dtoPar.setUnitName(param.getUnitName());

			typeParList.add(dtoPar);
		}

		for (DiscreteParameter param : actualDisParams) {
			TypeParameterDTO dtoPar = new TypeParameterDTO();
			dtoPar.setParametersType("discreteParameters");
			dtoPar.setDescription(param.getDescription());
			dtoPar.setUnitName(param.getUnitName());

			typeParList.add(dtoPar);
		}

		List<TypeParameterDTO> expectedTypeParList = expected.getParameters();
		Assert.assertEquals(expectedTypeParList.size(), typeParList.size());
		for (int i = 0; i < expectedTypeParList.size(); i++) {
			TypeParameterDTO exp = expectedTypeParList.get(i), act = typeParList.get(i);
			Assert.assertEquals(act.getUnitName(), exp.getUnitName());
			Assert.assertEquals(act.getParametersType(), exp.getParametersType());
			Assert.assertEquals(act.getDescription(), exp.getDescription());
		}
	}

	@Test(dataProvider="PrepareDataForDeletion", priority=7)
	public void delete(ResourceType res) {
		logger.info("Size of ResourceTypeRep before: "+resTypeRep.count());
		
		boolean actual = resTypeServ.delete(res);
		Assert.assertEquals(actual, false);
		Resource resource = resRep.findByType(res).get(0);
		resRep.delete(resource);
		actual = resTypeServ.delete(res);
		Assert.assertEquals(actual, true);
		
		logger.info("Size of ResourceTypeRep after: "+resTypeRep.count());
	}
}
