package org.registrator.community.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PrintServiceImpl implements PrintService {
	
	@Autowired
	InquiryRepository inquiryRepository;

	
	
	@Override
	public Document printProcuration(Integer inquiryId) {
		Inquiry inquiry = inquiryRepository.getOne(inquiryId);
		User user = inquiry.getUser();
		Address address = user.getAddress().get((user.getAddress().size() - 1));

		User registrator = inquiry.getRegistrator();

		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));

		String identifier = inquiry.getResource().getIdentifier();


		Document document=null;
		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)) {
			// print = printOutputProcuration(userName, userAddress, date,
			// registratorName,
			// registratorAddress, identifier);
			Date currentDate = new Date();
			
			try {
				String date=String.valueOf(currentDate.getYear());
				char parsedDate[]=date.toCharArray();
				 date=""+parsedDate[1]+parsedDate[2];
				
				
				document=createPdf("D:\\file.pdf", user.getFirstName(), user.getLastName(), user.getMiddleName(), "79042",
						"Україна", address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat(), String.valueOf(currentDate.getDate()),
						currentMonth(String.valueOf(currentDate.getMonth())),date,
						registrator.getFirstName(), registrator.getLastName(), registrator.getMiddleName(),
						addressRegistrator.getPostCode(), "Україна", addressRegistrator.getCity(), addressRegistrator.getStreet(),
						addressRegistrator.getBuilding(), addressRegistrator.getFlat(), identifier);
			} catch (IOException e) {				
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		return document;
	}

	
	/**
	 * @author Vitalii Horban
	 * change current month to string representation in Ukrainian
	 */
	private String currentMonth(String month) {

		if (month.equals("0"))
			return "Січня";
		else if (month.equals("1"))
			return "Лютого";
		else if (month.equals("2"))
			return "Березня";
		else if (month.equals("3"))
			return "Квітня";
		else if (month.equals("4"))
			return "Травня";
		else if (month.equals("5"))
			return "Червня";
		else if (month.equals("6"))
			return "Липня";
		else if (month.equals("7"))
			return "Серпня";
		else if (month.equals("8"))
			return "Вересня";
		else if (month.equals("9"))
			return "Жовтня";
		else if (month.equals("10"))
			return "Листопада";
		else if (month.equals("11"))
			return "Грудня";
		return "UNDEFINED";
	}
	/**
	 * @author Vitalii Horban
	 * creates pdf document "Доручення про надання витягу " 
	 */
	private  Document createPdf(String destination, String firstName, String lastName, String middleName,
			String zipcode, String country, String city, String streetName, String streetNumber, String homeNumber,
			String currentDay, String currentMonth, String currentYear, String registratorFirtstName,
			String registratorLastName, String registratorMiddleName, String registratorZipcode,
			String registratorCountry, String registratorCity, String registratorStreetName,
			String registratorStreetNumber, String registratorHomeNumber, String objectNumber)
					throws IOException, DocumentException {
		// left, right, top ,buttom
		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);
		FileOutputStream fis = new FileOutputStream(destination);
		PdfWriter pdfwr = PdfWriter.getInstance(document, fis);
		document.open();

		// звичайний
		BaseFont timesNewRomanBase = BaseFont.createFont("Times New Roman/TIMES.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanBase = new Font(timesNewRomanBase);
		// жирний
		BaseFont timesNewRomanFat = BaseFont.createFont("Times New Roman/TIMESBD.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanFat = new Font(timesNewRomanBase, 14, Font.BOLD);

		// жирний-курсив
		BaseFont timesNewRomanFat_Italic = BaseFont.createFont("Times New Roman/TIMESBI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanFat_Italic = new Font(timesNewRomanBase, 9, Font.BOLDITALIC);
		// курсив
		BaseFont timesNewRomanItalic = BaseFont.createFont("Times New Roman/TIMESI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanItalic = new Font(timesNewRomanBase, 14, Font.ITALIC);

		// add herb.png image
//		String imageUrl = "herb.png";
		URL imageURL=PrintServiceImpl.class.getResource("/images/herb.png");
		
		Image image = Image.getInstance(imageURL);
		image.scaleAbsolute(28f, 39f);
		image.setAlignment(Element.ALIGN_CENTER);
		document.add(image);

		Paragraph header = new Paragraph("Народ України", fontTimesNewRomanFat);
		header.getFont().setSize(13f);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);

		Paragraph header2 = new Paragraph("Безпосередня влада народу", fontTimesNewRomanFat);
		header2.getFont().setSize(16f);
		header2.setAlignment(Element.ALIGN_CENTER);
		document.add(header2);

		Paragraph header3 = new Paragraph("Людина як найвища соціальна цінність в Україні", fontTimesNewRomanFat);
		header3.getFont().setSize(19f);
		header3.setAlignment(Element.ALIGN_CENTER);
		document.add(header3);

		Paragraph header4 = new Paragraph("", fontTimesNewRomanFat);
		header4.getFont().setSize(22f);
		header4.setAlignment(Element.ALIGN_CENTER);
		String fullName = lastName + " " + firstName + " " + middleName;
		header4.add(fullName);
		document.add(header4);

		Paragraph blank = new Paragraph("   ");
		blank.getFont().setSize(10f);
		document.add(blank);

		Paragraph header5 = new Paragraph("Адреса для листування: ", fontTimesNewRomanBase);
		header5.getFont().setSize(10f);
		header5.add(zipcode);
		header5.add(" , ");
		header5.add(country);
		header5.add(" , ");
		header5.add(city);
		header5.add(" , ");
		header5.add(streetName);
		header5.add(" ");
		header5.add(streetNumber);
		header5.add("/");
		header5.add(homeNumber);
		document.add(header5);

		Paragraph header6 = new Paragraph("", fontTimesNewRomanBase);

		header6.getFont().setSize(10f);
		header6.add("«" + currentDay + "»");
		header6.add(" ");
		header6.add(currentMonth);
		header6.add(" ");
		header6.add("20"+currentYear);
		header6.add(" року");
		document.add(header6);

		Paragraph header7 = new Paragraph("Народному реєстратору", fontTimesNewRomanFat);
		header7.setAlignment(Element.ALIGN_RIGHT);
		header7.getFont().setSize(12f);
		document.add(header7);

		Paragraph header8 = new Paragraph("", fontTimesNewRomanFat);
		header8.setAlignment(Element.ALIGN_RIGHT);
		String fullRegistratorName = registratorLastName + " " + registratorFirtstName + " " + registratorMiddleName;
		header8.add(fullRegistratorName);
		header8.getFont().setSize(12f);
		document.add(header8);

		Paragraph header9 = new Paragraph("", fontTimesNewRomanBase);
		header9.setAlignment(Element.ALIGN_RIGHT);
		header9.add("(вул. " + registratorStreetName);
		header9.add(registratorStreetNumber + " / " + registratorHomeNumber + ", ");
		header9.add("м. " + registratorCity + ", " + registratorZipcode + ")");
		header9.getFont().setSize(12f);
		document.add(header9);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header10 = new Paragraph("Доручення", fontTimesNewRomanFat);
		header10.setAlignment(Element.ALIGN_CENTER);
		header10.getFont().setSize(22f);
		document.add(header10);

		Paragraph header11 = new Paragraph("про надання витягу з Децентралізованого", fontTimesNewRomanBase);
		header11.setAlignment(Element.ALIGN_CENTER);
		header11.getFont().setSize(12f);
		document.add(header11);

		Paragraph header12 = new Paragraph("майнового реєстру природних ресурсів України", fontTimesNewRomanBase);
		header12.setAlignment(Element.ALIGN_CENTER);
		header12.getFont().setSize(12f);
		header12.add(new Chunk("Доручення"));
		document.add(header12);

		document.add(Chunk.NEWLINE);

		Paragraph header13 = new Paragraph("Я, людина ", fontTimesNewRomanBase);
		header13.setAlignment(Element.ALIGN_LEFT);
		header13.add(fullName);
		header13.add(" доручаю надати мені витяг з Децентралізованого майнового реєстру природних ресурсів України ");
		header13.getFont().setSize(12f);
		header13.add("щодо об’єкту з номером ");
		header13.add(objectNumber);
		document.add(header13);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header14 = new Paragraph("Людина ", fontTimesNewRomanBase);
		header14.setAlignment(Element.ALIGN_LEFT);
		header14.getFont().setSize(12f);
		header14.add(Chunk.TABBING);
		char[] firstNameArray = firstName.toCharArray();
		char[] lastNameArray = lastName.toCharArray();
		String firstNameC = "" + firstNameArray[0];
		String lastNameC = "" + lastNameArray[0];
		String nameForFooter = lastName + " " + firstNameC.toUpperCase() + ". " + lastNameC.toUpperCase() + ".";
		header14.add(nameForFooter);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);
		header14.add(Chunk.TABBING);

		header14.add("______________(підпис)");
		document.add(header14);

		document.close();
		return document;
	}
}
