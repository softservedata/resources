package org.registrator.community.service;

import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.impl.PrintServiceImpl;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.*;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

public class PrintServiceTest {

    @InjectMocks
    private PrintService printServiceImpl = new PrintServiceImpl();
    @Mock
    private InquiryRepository inquiryRepository;
    @Mock
    private PolygonRepository polygonRepository;
    @Mock
    private ResourceDiscreteValueService resourceDiscrete;
    @Mock
    private Logger logger;

    private static final int META_INFORMATION_SIZE = 2200;
    private static final Integer ID = 1; // InquiryID
    private static TerritorialCommunity globalTerritorialCommunity;
    private static User user, registrator;
    private static ResourceType land;
    private static Tome tome;
    private static Resource resource;
    private static Inquiry inquiry;

    @BeforeMethod
    public void init() {

        MockitoAnnotations.initMocks(this);
        globalTerritorialCommunity = new TerritorialCommunity();
        globalTerritorialCommunity.setName("Україна");

        Role roleUser = new Role(RoleType.USER,"description");
        Role roleRegistrator = new Role(RoleType.REGISTRATOR,"description");

        user = new User("user","$2a$10$Wcuw6mLD18wVT5diGYncJeVyL8J1bTSIly2IbLUX2bJ.UWZPC.qS.",
                roleUser,"Іван","Головатий","Сергійович","ivan@gmail.com","ACTIVE");
        user.setDateOfAccession(new Date());
        user.setTerritorialCommunity(globalTerritorialCommunity);
        user.setAddress(Arrays.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
        user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));

        registrator = new User("registrator","$2a$10$KJdq1wmP3MctLh.lEdAuseUCnSRdhJo8S7qwaZHFEUoGhfjOsOnrm",
                roleRegistrator,"Євген","Михалкевич","Сергійович","evgen@gmail.com","ACTIVE");
        registrator.setDateOfAccession(new Date());
        registrator.setTerritorialCommunity(globalTerritorialCommunity);
        registrator.setAddress(Arrays.asList(
                new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12")));
        registrator.setPassport(Arrays.asList(new PassportInfo(registrator, "ББ", "11111", "Народом України")));

        land = new ResourceType("земельний");
        tome = new Tome(registrator, "12345");

        resource = new Resource(land, "111111", "ліс", registrator, new Date(), "active", tome, "підстава на внесення");

        inquiry = new Inquiry("OUTPUT", new Date(), user, registrator, resource);

    }


    @Test
    public void testProcuration() throws Exception{

        when(inquiryRepository.getOne(ID)).thenReturn(inquiry);

        ByteArrayOutputStream bos = printServiceImpl.printProcuration(ID);
        byte [] array = bos.toByteArray();
        //byte [] subArray = new byte[array.length - 2200];
        byte [] subArray = Arrays.copyOf(array, array.length - META_INFORMATION_SIZE);

        File file = new File("D:\\procuration.pdf");
        byte [] arrayFromFile = readContentIntoByteArray(file);

        System.out.println("array.length " + array.length);
        System.out.println("subArray.length " + subArray.length);
        System.out.println("arrayFromFile.length " + arrayFromFile.length);

        OutputStream os = null;
        try {
            File f = new File("D:\\procurationTest.pdf");
            os = new FileOutputStream(f);
            bos.writeTo(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }

        Assert.assertArrayEquals(subArray, arrayFromFile);

    }

    private static byte[] readContentIntoByteArray(File file) throws Exception
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

    /*@Test
    public void testPrintProcurationUsesInquiryRepository() throws Exception {

        //userAddressList = new ArrayList<>();
        userAddressList.add(userAddress);

        //registratorAddressList = new ArrayList<>();
        registratorAddressList.add(registratorAddress);

        when(user.getFirstName()).thenReturn("FirstName");
        when(user.getMiddleName()).thenReturn("MiddleName");
        when(user.getLastName()).thenReturn("LastName");
        when(user.getAddress()).thenReturn(userAddressList);

        doReturn(userAddress).when(user.getAddress()).get(user.getAddress().size() - 1);

        when(user.getAddress().get(userAddressList.size())).thenReturn(userAddress);
        when(userAddress.getPostCode()).thenReturn("PostCode");
        when(userAddress.getCity()).thenReturn("City");
        when(userAddress.getStreet()).thenReturn("Street");
        when(userAddress.getBuilding()).thenReturn("Building");
        when(userAddress.getFlat()).thenReturn("Flat");

        when(inquiry.getRegistrator()).thenReturn(registrator);
        when(inquiry.getRegistrator().getFirstName()).thenReturn("FirstName");
        when(inquiry.getRegistrator().getMiddleName()).thenReturn("MiddleName");
        when(inquiry.getRegistrator().getLastName()).thenReturn("LastName");
        when(inquiry.getRegistrator().getAddress()).thenReturn(registratorAddressList);
        when(registratorAddress.getPostCode()).thenReturn("PostCode");
        when(registratorAddress.getCity()).thenReturn("City");
        when(registratorAddress.getStreet()).thenReturn("Street");
        when(registratorAddress.getBuilding()).thenReturn("Building");
        when(registratorAddress.getFlat()).thenReturn("Flat");


        printServiceImpl.printProcuration(ID);

        verify(inquiryRepository, times(2)).getOne(ID);
        verify(inquiry).getInquiryType();
        verify(inquiry).getUser();
        verify(user, times(3)).getAddress();
        //verify(user).getFirstName();



        //verify(inquiry, times(1)).getRegistrator();
        //verify(userAddress).getPostCode();

    }*/
}

