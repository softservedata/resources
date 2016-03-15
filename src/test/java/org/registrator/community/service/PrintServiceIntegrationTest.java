package org.registrator.community.service;

import java.io.*;
import java.util.Arrays;

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
    PrintService printServiceImpl;

    private static final int META_INFORMATION_SIZE = 2300;// contains CreationDate in ms, can't compare different pdf files

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

    //@Test
    public void testPrintProcurationSaveCorrectData() throws IOException {

        ByteArrayOutputStream bos = printServiceImpl.printProcuration(1);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\surefire-reports\\testng-junit-results\\procuration.pdf");
        byte [] expected = readContentIntoByteArray(file);

        Assert.assertArrayEquals(actual, expected);

    }

    //@Test
    public void testPrintExtractSaveCorrectData() throws IOException {

        ByteArrayOutputStream bos = printServiceImpl.printExtract(1);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\surefire-reports\\testng-junit-results\\extract.pdf");
        byte [] expected = readContentIntoByteArray(file);

        Assert.assertArrayEquals(actual, expected);

    }

    //@Test
    public void testPrintProcurationOnSubmitInfoSaveCorrectData() throws IOException {

        ByteArrayOutputStream bos = printServiceImpl.printProcurationOnSubmitInfo(2);

        byte [] array = bos.toByteArray();
        bos.close();
        byte [] actual = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File(".\\target\\surefire-reports\\testng-junit-results\\procurationOnSubmit.pdf");
        byte [] expected = readContentIntoByteArray(file);

        Assert.assertArrayEquals(actual, expected);
    }

    private static byte[] readContentIntoByteArray(File file) throws IOException
    {
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length() - META_INFORMATION_SIZE];
        try
        {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            fileInputStream.close();
        }
        return bFile;
    }

}


