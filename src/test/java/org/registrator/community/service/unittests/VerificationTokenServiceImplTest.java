package org.registrator.community.service.unittests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.registrator.community.dao.VerificationTokenRepository;
import org.registrator.community.entity.VerificationToken;
import org.registrator.community.enumeration.TokenType;
import org.registrator.community.service.VerificationTokenService;
import org.registrator.community.service.impl.VerificationTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class VerificationTokenServiceImplTest {
	@Mock
	private VerificationTokenRepository verificationTokenRepository;
	@Mock
	private Logger logger;
	
	@InjectMocks
	private VerificationTokenService verificationTokenService = new VerificationTokenServiceImpl();

	private final static int DESIRED_RESOURCES = 10;
	private final static int PLUS_TIME = VerificationTokenServiceImpl.PASSWORD_TOKEN_EXPIRY_TIME;
	

	@BeforeClass
	public void bindMocks(){
		MockitoAnnotations.initMocks(this);
		
		Logger inLogger = LoggerFactory.getLogger(this.getClass());
		doAnswer(new Answer<Void>(){
			public Void answer(InvocationOnMock invo){
				String message = invo.getArgumentAt(0, String.class);
				inLogger.info(message);
				return null;
			}
		}).when(logger).info(anyString());
	}

	@BeforeClass
	public void prepareMockVerTokenRep() {
		List<VerificationToken> mockForTokenRep = new ArrayList<VerificationToken>();

		logger.info("Preparing method overrun for mocked VerificationToken Repository");
		when(verificationTokenRepository.count()).then(new Answer<Long>() {
			public Long answer(InvocationOnMock invo) {
				return (long) mockForTokenRep.size();
			}
		});

		when(verificationTokenRepository.save(any(VerificationToken.class))).then(new Answer<VerificationToken>() {
			public VerificationToken answer(InvocationOnMock invo) {
				VerificationToken token = invo.getArgumentAt(0, VerificationToken.class);
				mockForTokenRep.add(token);
				return token;
			}
		});

		when(verificationTokenRepository.findVerificationTokenByToken(anyString())).then(new Answer<VerificationToken>() {
			public VerificationToken answer(InvocationOnMock invo) {
				String tokenName = invo.getArgumentAt(0, String.class);
				for (VerificationToken token : mockForTokenRep) {
					if (token.getToken() == tokenName) {
						return token;
					}
				}
				return null;
			}
		});

		when(verificationTokenRepository.findVerificationTokenByTokenAndTokenType(anyString(), any(TokenType.class)))
				.then(new Answer<VerificationToken>() {
					public VerificationToken answer(InvocationOnMock invo) {
						String tokenName = invo.getArgumentAt(0, String.class);
						TokenType type = invo.getArgumentAt(1, TokenType.class);

						for (VerificationToken token : mockForTokenRep) {
							if (token.getTokenType() == type && token.getToken() == tokenName) {
								return token;
							}
						}
						return null;
					}
				});

		doAnswer(new Answer<Void>() {
			public Void answer(InvocationOnMock invo) {
				VerificationToken token = invo.getArgumentAt(0, VerificationToken.class);
				mockForTokenRep.remove(token);
				return null;
			}
		}).when(verificationTokenRepository).delete(any(VerificationToken.class));

		when(verificationTokenRepository.findTokenByEmail(anyString())).then(new Answer<VerificationToken>() {
			public VerificationToken answer(InvocationOnMock invo) {
				String email = invo.getArgumentAt(0, String.class);

				for (VerificationToken token : mockForTokenRep) {
					if (token.getUserEmail().equals(email)) {
						return token;
					}
				}
				return null;
			}
		});
	}

	// DataProviders

	@DataProvider(name = "formDataForTokenCreation")
	public Object[][] formDataForTokens() {
		Object[][] tmp = new Object[DESIRED_RESOURCES][];
		Date now = new Date();
		Random rand = new Random();

		logger.info("DataProviders: Running the \"DataForTokenCreation\" DataProvider");

		String fakeRandomUUID = verificationTokenService.createHashForPasswordToken() + rand.nextInt(1000);
		String email = "someMailAddress#%03d@mail.me";

		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = new Object[] { fakeRandomUUID, String.format(email, rand.nextInt(100)), now };
		}

		return tmp;
	}

	// Tests

	@Test
	public void createHashForPasswordToken() {
		logger.info("Testing the random hash string creation method");

		for (int i = 0; i < DESIRED_RESOURCES; i++) {
			String generated = verificationTokenService.createHashForPasswordToken();
			Assert.assertNotNull(generated);
		}
	}

	@Test(dataProvider = "formDataForTokenCreation")
	public void saveAndDeletePasswordVerificationToken(String uuid, String email, Date date) {
		logger.info(
				"Testing the creation and the removal of the Password VerificationToken. Using data (hashString, email, date) :"
						+ uuid + ", " + email + ", " + date);

		Date forSynt = new Date(date.getTime() + PLUS_TIME);

		VerificationToken expected = new VerificationToken(uuid, email, forSynt, TokenType.RECOVER_PASSWORD),
				actual = verificationTokenService.savePasswordVerificationToken(email, date);

		Assert.assertEquals(expected.getUserEmail(), actual.getUserEmail());
		Assert.assertEquals(expected.getExpiryDate(), actual.getExpiryDate());

		VerificationToken extraCheck = verificationTokenRepository.findTokenByEmail(actual.getUserEmail());
		Assert.assertNotNull(extraCheck);

		boolean isDeleted = verificationTokenService.deletePasswordVerificationTokenByEmail(extraCheck.getUserEmail());
		Assert.assertEquals(isDeleted, true);
	}

	@Test(dataProvider = "formDataForTokenCreation")
	public void deleteVerificationToken(String uuid, String email, Date date) {
		logger.info("Testing the FindAndDelete VerificationToken method. Using data (hashString, email, date) :" + uuid
				+ ", " + email + ", " + date);

		date.setTime(date.getTime() + PLUS_TIME);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		verificationTokenRepository.save(syntetical);
		verificationTokenService.deleteVerificationToken(syntetical);
		syntetical = verificationTokenRepository.findVerificationTokenByToken(syntetical.getToken());
		Assert.assertNull(syntetical);
	}

	@Test(dataProvider = "formDataForTokenCreation")
	public void findVerificationTokenByTokenAndTokenType(String uuid, String email, Date date) {
		logger.info(
				"Testing the find the VerificationToken method, which is using Token name and TokenType data (hashString, email, date) :"
						+ uuid + ", " + email + ", " + date);

		date.setTime(date.getTime() + PLUS_TIME);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		verificationTokenRepository.save(syntetical);
		syntetical = verificationTokenService.findVerificationTokenByTokenAndTokenType(syntetical.getToken(), syntetical.getTokenType());
		Assert.assertNotNull(syntetical);
	}

	@Test(dataProvider = "formDataForTokenCreation")
	public void isExistValidVerificationToken(String uuid, String email, Date date) {
		logger.info("Testing the isValid(by time) VerificationToken method. Using data (hashString, email, date) :"
				+ uuid + ", " + email + ", " + date);

		date.setTime(date.getTime() + PLUS_TIME);
		VerificationToken syntetical = new VerificationToken(uuid, email, date, TokenType.RECOVER_PASSWORD);
		verificationTokenRepository.save(syntetical);
		boolean manualCheck = syntetical.getExpiryDate().getTime() > System.currentTimeMillis(),
				formedCheck = verificationTokenService.isExistValidVerificationToken(syntetical.getToken());
		Assert.assertEquals(manualCheck, formedCheck);
	}

}
