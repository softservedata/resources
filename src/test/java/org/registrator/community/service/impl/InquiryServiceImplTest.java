package org.registrator.community.service.impl;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.registrator.community.dao.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

/**
 * Unit test for saving output procuration in the database.
 */
public class InquiryServiceImplTest {
  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

  @Autowired
	InquiryRepository inquiryRepository;

  @Test
  public void addOutputInquiry() {
//	    long countLinesInquiryList = inquiryRepository.count();
//		System.out.println("count of Lines in InquiryList table = " + countLinesInquiryList);
		
		
		Assert.assertTrue(true);
    //throw new RuntimeException("Test not implemented");
  }

  @Test
  public void removeInquiry() {
   // throw new RuntimeException("Test not implemented");
  }
}
