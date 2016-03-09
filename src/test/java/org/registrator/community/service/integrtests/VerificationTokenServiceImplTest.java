package org.registrator.community.service.integrtests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.VerificationTokenRepository;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@ActiveProfiles("testing")
@ContextConfiguration(classes = { TestingConfiguration.class, LoggingConfig.class, SpringRootConfig.class })
public class VerificationTokenServiceImplTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private VerificationTokenRepository verRep;
	@Autowired
	private VerificationTokenService verServ;

	private Date date = new Date();
	private List<VerificationToken> cTokenList = new ArrayList<VerificationToken>();
	private int desiredResources = 10;

	// DataProviders
	@DataProvider(name = "ProviderForTokenFormation")
	public Object[][] formEmailStrings() {
		Object[][] tmp = new Object[desiredResources][1];
		String emailMask = "tokenEmail#%03d@gmail.com";

		for (int i = 0; i < tmp.length; i++) {
			tmp[i][0] = String.format(emailMask, i);
		}
		return tmp;
	}

	@DataProvider(name = "ProviderForSearchTypeOperations")
	public Object[][] formSearchData() {
		Object[][] tmp = new Object[cTokenList.size()][2];

		Date expired = new Date(0);
		boolean isExpired = false;
		for (int i = 0; i < tmp.length; i++) {
			VerificationToken tokInList = cTokenList.get(i),
					tok = verRep.findVerificationTokenByToken(tokInList.getToken());
			isExpired = false;
			if (i % 2 == 0) {
				verRep.delete(tok);

				tok.setExpiryDate(expired);
				tokInList.setExpiryDate(expired);

				isExpired = true;
				tok = verRep.save(tok);
			}
			tmp[i] = new Object[] { tok, isExpired };
		}
		return tmp;
	}
	
	// Tests

	@Test(priority=1)
	public void createHashForPasswordToken() {
		for (int i = 0; i < desiredResources; i++) {
			String generated = verServ.createHashForPasswordToken();
			Assert.assertNotNull(generated);
		}
	}

	@Test(dataProvider = "ProviderForTokenFormation", priority=2)
	public void savePasswordVerificationToken(String email) {
		VerificationToken actual = verServ.savePasswordVerificationToken(email, date),
				expected = new VerificationToken(actual.getToken(), email, actual.getExpiryDate(),
						TokenType.RECOVER_PASSWORD);

		Assert.assertEquals(expected.getUserEmail(), actual.getUserEmail());
		Assert.assertEquals(expected.getExpiryDate(), actual.getExpiryDate());

		VerificationToken extraCheck = verRep.findTokenByEmail(actual.getUserEmail());
		Assert.assertNotNull(extraCheck);

		cTokenList.add(actual);
	}

	@Test(dataProvider = "ProviderForSearchTypeOperations", priority=3)
	public void isExistValidVerificationToken(VerificationToken tok, boolean isExpired) {
		boolean manualCheck = tok.getExpiryDate().getTime() > System.currentTimeMillis(),
				formedCheck = verServ.isExistValidVerificationToken(tok.getToken());
		Assert.assertEquals(formedCheck, !isExpired);
		Assert.assertEquals(manualCheck, formedCheck);
	}

	@Test(dataProvider = "ProviderForSearchTypeOperations", priority=4)
	public void findVerificationTokenByTokenAndTokenType(VerificationToken expected, boolean isExpired) {
		VerificationToken actual = verServ.findVerificationTokenByTokenAndTokenType(expected.getToken(),
				expected.getTokenType());

		Assert.assertEquals(actual.getToken(), expected.getToken());
		Assert.assertEquals(actual.getUserEmail(), expected.getUserEmail());
		Assert.assertEquals(actual.getExpiryDate().getTime(), expected.getExpiryDate().getTime());
		Assert.assertEquals(actual.getId(), expected.getId());
	}

	@Test(priority=5)
	public void deletePasswordVerificationTokenByEmailAndByTokenName() {
		int listSize = cTokenList.size();
		long repSize = verRep.count();
		
		logger.info("Size before cleanUp: "+repSize);
		for(int i = 0; i< listSize; i++){
			VerificationToken tok = verRep.findVerificationTokenByToken(cTokenList.get(i).getToken());
			if(i%2==0){
				verServ.deletePasswordVerificationTokenByEmail(tok.getUserEmail());
			}else{
				verServ.deleteVerificationToken(tok);
			}
			tok = verRep.findVerificationTokenByToken(cTokenList.get(i).getToken());
			Assert.assertNull(tok);
		}
		Assert.assertEquals(verRep.count(), repSize-listSize);
		logger.info("Size after cleanUp: "+verRep.count());
		cTokenList.clear();
	}
}
