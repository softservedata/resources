package org.registrator.community.security;
/*
import org.springframework.test.context.ActiveProfiles;
import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestingConfiguration.class)
@ActiveProfiles("testing")
public class SpringSecurityTest {

//	private static Logger LOG = Logger.getLogger(SpringSecurityTest.class);

	@Autowired
	public UserService userService;

	@Autowired
	public UserDetailsService userDetailsService;

	@Autowired
	public ResourceService resourceService;

	@Autowired
	ResourceTypeService resourceTypeService;

	@Autowired
	PrintService printService;

	@Rule
public	TestWatcher testWatcher = new TestWatcher() {

		protected void succeeded(org.junit.runner.Description description) {
//			LOG.info(description.getMethodName());
			System.out.println("<<SUCCESS>> - " + description.getMethodName());
		};

		protected void failed(Throwable e, org.junit.runner.Description description) {
//			LOG.error(description.getMethodName());
			System.out.println("<<FAILED>> - " + description.getMethodName());
		};

	};

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testOne() {
		userService.getAllInactiveUsers();
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testTwo() {
		userService.getAllRegistratedUsers();
	}

	@Test
	@WithMockUser(roles = { "ADMIN", "REGISTRATOR" })
	public void testThree() {
		resourceService.count();
	}

	@Test
	@WithMockUser(roles = {"REGISTRATOR" })
	public void testFour() {
		resourceTypeService.findAll();
	}

	@Test
	@WithMockUser(roles = { "USER", "REGISTRATOR" })
	public void testFive() {
		printService.printProcuration(1);

	}

	@Test
	@WithMockUser(roles = { "REGISTRATOR" })
	public void testSix() {
		printService.printExtract(1);
	}

	@Test
	@WithMockUser(roles = { "USER" })
	public void testSeven() {
		printService.printProcurationOnSubmitInfo(1);
	}

}
*/
