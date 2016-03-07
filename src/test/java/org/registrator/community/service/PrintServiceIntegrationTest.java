package org.registrator.community.service;

import java.io.*;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.registrator.community.config.LoggingConfig;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testing")
@ContextConfiguration(classes={TestingConfiguration.class,LoggingConfig.class,SpringRootConfig.class})
@WebAppConfiguration
public class PrintServiceIntegrationTest {

    private static Logger LOG=Logger.getLogger(PrintServiceIntegrationTest.class);

    @Autowired
    PrintService printService;


    @Rule
    public TestWatcher testWatcher = new TestWatcher() {

        protected void succeeded(org.junit.runner.Description description) {
            LOG.info(description.getMethodName());
            System.out.println("<<SUCCESS>> - " + description.getMethodName());

        }

        protected void failed(Throwable e, org.junit.runner.Description description) {

            LOG.error(description.getMethodName());
            System.out.println("<<FAILED>> - " + description.getMethodName());
        }

    };


    @Test
    public void testPrintProcurationExistsCorrectFileFormat() throws IOException {

        OutputStream os = null;
        File file = null;
        ByteArrayOutputStream bos = printService.printProcuration(1);
        try {
            file = new File("D:\\procuration.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

        if(!file.exists()){
            Assert.fail("file is not created");
        }
        String [] name = file.getName().split("\\.");
        if(!name[name.length-1].equals("pdf")){
            Assert.fail("created file is not pdf format");
        }

    }

	@Test
	public void testPrintExtractExistsCorrectFileFormat() throws IOException {

        OutputStream os = null;
        File file = null;
        ByteArrayOutputStream bos = printService.printExtract(1);
        try {
            file = new File("D:\\extract.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

		if(!file.exists()){
			Assert.fail("file is not created");
		}
		String [] name = file.getName().split("\\.");
		if(!name[name.length-1].equals("pdf")){
			Assert.fail("created file is not pdf format");
		}
	}

	@Test
	public void testPrintProcurationOnSubmitInfoCorrectFileFormat() throws IOException {

        OutputStream os = null;
        File file = null;
        ByteArrayOutputStream bos = printService.printProcurationOnSubmitInfo(2);
        try {
            file = new File("D:\\procurationOnSubmit.pdf");
            os = new FileOutputStream(file);
            bos.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

		if(!file.exists()){
			Assert.fail("file is not created");
		}
		String [] name = file.getName().split("\\.");
		if(!name[name.length-1].equals("pdf")){
			Assert.fail("created file is not pdf format");
		}
	}

}


