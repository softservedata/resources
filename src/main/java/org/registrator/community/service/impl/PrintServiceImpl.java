package org.registrator.community.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
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
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PrintServiceImpl implements PrintService {

	@Autowired
	InquiryRepository inquiryRepository;

	@Autowired
	PolygonRepository polygonRepository;

	@Autowired
	AreaRepository areaRepository;
	/**
	 * @author Vitalii Horban creates pdf document
	 *         "Доручення про надання витягу "
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Document printProcuration(Integer inquiryId) {
		Inquiry inquiry = inquiryRepository.getOne(inquiryId);
		User user = inquiry.getUser();
		Address address = user.getAddress().get((user.getAddress().size() - 1));

		User registrator = inquiry.getRegistrator();

		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));

		String identifier = inquiry.getResource().getIdentifier();

		Document document = null;
		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)) {
			// print = printOutputProcuration(userName, userAddress, date,
			// registratorName,
			// registratorAddress, identifier);
			Date currentDate = new Date();

			try {
				String date = String.valueOf(currentDate.getYear());
				char parsedDate[] = date.toCharArray();
				date = "" + parsedDate[1] + parsedDate[2];

				document = createMandatToExtractPdf("D:\\file.pdf", user.getFirstName(), user.getLastName(),
						user.getMiddleName(), "79042", "Україна", address.getCity(), address.getStreet(),
						address.getBuilding(), address.getFlat(), String.valueOf(currentDate.getDate()),
						currentMonth(String.valueOf(currentDate.getMonth())), date, registrator.getFirstName(),
						registrator.getLastName(), registrator.getMiddleName(), addressRegistrator.getPostCode(),
						"Україна", addressRegistrator.getCity(), addressRegistrator.getStreet(),
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
	 * @author Vitalii Horban change current month to string representation in
	 *         Ukrainian
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

	
	private Document createMandatToExtractPdf(String destination, String firstName, String lastName, String middleName,
			String zipcode, String country, String city, String streetName, String streetNumber, String homeNumber,
			String currentDay, String currentMonth, String currentYear, String registratorFirtstName,
			String registratorLastName, String registratorMiddleName, String registratorZipcode,
			String registratorCountry, String registratorCity, String registratorStreetName,
			String registratorStreetNumber, String registratorHomeNumber, String objectNumber)
					throws IOException, DocumentException {
		// left, right, top ,buttom
		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);
		FileOutputStream fis = new FileOutputStream(destination);
		@SuppressWarnings("unused")
		PdfWriter pdfwr = PdfWriter.getInstance(document, fis);
		document.open();

		// звичайний
		BaseFont timesNewRomanBase = BaseFont.createFont("Times New Roman/TIMES.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanBase = new Font(timesNewRomanBase);
		// жирний
		@SuppressWarnings("unused")
		BaseFont timesNewRomanFat = BaseFont.createFont("Times New Roman/TIMESBD.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanFat = new Font(timesNewRomanBase, 14, Font.BOLD);

		// жирний-курсив
		@SuppressWarnings("unused")
		BaseFont timesNewRomanFat_Italic = BaseFont.createFont("Times New Roman/TIMESBI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		@SuppressWarnings("unused")
		Font fontTimesNewRomanFat_Italic = new Font(timesNewRomanBase, 9, Font.BOLDITALIC);
		// курсив
		@SuppressWarnings("unused")
		BaseFont timesNewRomanItalic = BaseFont.createFont("Times New Roman/TIMESI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		@SuppressWarnings("unused")
		Font fontTimesNewRomanItalic = new Font(timesNewRomanBase, 14, Font.ITALIC);

		// add herb.png image
		// String imageUrl = "herb.png";
		URL imageURL = PrintServiceImpl.class.getResource("/images/herb.png");

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
		header6.add("20" + currentYear);
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

	/**
	 * @author Vitalii Horban creates pdf document "Витяг"
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Document printExtract(Integer inquiryId) {
		Inquiry inquiry = inquiryRepository.getOne(inquiryId);
		User user = inquiry.getUser();
		@SuppressWarnings("unused")
		Address address = user.getAddress().get((user.getAddress().size() - 1));
		User registrator = inquiry.getRegistrator();
		Address addressRegistrator = registrator.getAddress().get((registrator.getAddress().size() - 1));
		String identifier = inquiry.getResource().getIdentifier();
		Resource resource = inquiry.getResource();
		@SuppressWarnings("unused")
		ResourceType resType = inquiry.getResource().getType();
		String reasonForInclusion = inquiry.getResource().getReasonInclusion();
		Date inquireDate= inquiry.getDate();

		
		
		List<Polygon> polygon = polygonRepository.findByResource(resource);
		double totalLongetude = 0;
		double totalLatitude = 0;

		List<Double> northWidth = new ArrayList<>();
		List<Double> northWidthContinue = new ArrayList<>();
		List<Double> eastLength = new ArrayList<>();
		List<Double> eastLengthContinue = new ArrayList<>();
		List<Double> totalCoordinates = new ArrayList<>();

		for (Polygon p : polygon) {

			northWidth.add(p.getMaxLat());
			northWidthContinue.add(p.getMinLat());
			eastLength.add(p.getMaxLng());
			eastLengthContinue.add(p.getMinLng());

			totalCoordinates.add(p.getMaxLat());
			totalCoordinates.add(p.getMinLat());
			totalCoordinates.add(p.getMaxLng());
			totalCoordinates.add(p.getMinLng());

			List<Area> area = areaRepository.findByPolygon(p);

			for (Area a : area) {
				totalLongetude += a.getLongitude();
				totalLatitude += a.getLatitude();
			}
		}

		String linearSize = "" + String.format("%.4f", totalLongetude) + ":" + String.format("%.4f", totalLatitude)
				+ ":" + 0;

		Document document = null;
		if (inquiry.getInquiryType().equals(InquiryType.OUTPUT)) {

			Date currentDate = new Date();

			try {
				String dateCurrentYear = String.valueOf(currentDate.getYear());
				char parsedDate[] = dateCurrentYear.toCharArray();
				dateCurrentYear = "" + parsedDate[1] + parsedDate[2];

				String dateInquireYear = String.valueOf(currentDate.getYear());
				char parsedDateInquire[] = dateInquireYear.toCharArray();
				dateInquireYear = "" + parsedDateInquire[1] + parsedDateInquire[2];

				document = createExtractPdf("D:\\file.pdf", resource.getDescription(), "природний ресурс",
						String.valueOf(resource.getType().getTypeName()), totalCoordinates, linearSize,
						"відомості відсутні", "відомості відсутні", "відомості відсутні", "відомості відсутні",
						reasonForInclusion, registrator.getFirstName(), registrator.getLastName(),
						registrator.getMiddleName(), String.valueOf(addressRegistrator.getPostCode()), "Україна",
						addressRegistrator.getCity(), addressRegistrator.getStreet(),
						String.valueOf(addressRegistrator.getBuilding()), String.valueOf(addressRegistrator.getFlat()),
						identifier, String.valueOf(currentDate.getDate()),
						currentMonth(String.valueOf(currentDate.getMonth())), String.valueOf(currentDate.getMonth()),
						dateCurrentYear, String.valueOf(inquireDate.getDay()), String.valueOf(inquireDate.getMonth()),
						String.valueOf(inquireDate.getMonth()), dateInquireYear);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		return document;
	}

	
	private Document createExtractPdf(String destination, String objectName, String objectClass, String subObjectClass,

			List<Double> totalCoordinates, String linearObjectSize, String totalSquareOfObject, String weight,
			String objectPerimetr, String objectCapacity, String reasonForInclusion, String registratorFirtstName,
			String registratorLastName, String registratorMiddleName, String registratorZipcode,
			String registratorCountry, String registratorCity, String registratorStreetName,
			String registratorStreetNumber, String registratorHomeNumber, String objectNumber, String currentDay,
			String currentMonth, String currentMonthNumber, String currentYear, String requestDay,
			String requestMonthNumber, String requestMonth, String requestYear) throws IOException, DocumentException {
		// left, right, top ,buttom
		Document document = new Document(PageSize.A4, 38f, 38f, 38f, 35f);
		FileOutputStream fis = new FileOutputStream(destination);
		@SuppressWarnings("unused")
		PdfWriter pdfwr = PdfWriter.getInstance(document, fis);
		document.open();

		// звичайний
		BaseFont timesNewRomanBase = BaseFont.createFont("Times New Roman/TIMES.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanBase = new Font(timesNewRomanBase);
		// жирний
		@SuppressWarnings("unused")
		BaseFont timesNewRomanFat = BaseFont.createFont("Times New Roman/TIMESBD.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanFat = new Font(timesNewRomanBase, 14, Font.BOLD);

		// жирний-курсив
		@SuppressWarnings("unused")
		BaseFont timesNewRomanFat_Italic = BaseFont.createFont("Times New Roman/TIMESBI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		@SuppressWarnings("unused")
		Font fontTimesNewRomanFat_Italic = new Font(timesNewRomanBase, 9, Font.BOLDITALIC);
		// курсив
		@SuppressWarnings("unused")
		BaseFont timesNewRomanItalic = BaseFont.createFont("Times New Roman/TIMESI.TTF", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font fontTimesNewRomanItalic = new Font(timesNewRomanBase, 14, Font.ITALIC);

		// add herb.png image
		// String imageUrl = "herb.png";
		URL imageURL = PrintServiceImpl.class.getResource("/images/herb.png");
		Image image = Image.getInstance(imageURL);
		image.scaleAbsolute(28f, 39f);
		image.setAlignment(Element.ALIGN_CENTER);
		document.add(image);

		Paragraph header = new Paragraph("Територіальна громада міста Львова", fontTimesNewRomanFat);
		header.getFont().setSize(14f);
		header.setAlignment(Element.ALIGN_CENTER);
		document.add(header);

		Paragraph header2 = new Paragraph("Уповноважений з питань реєстрації (народний реєстратор)",
				fontTimesNewRomanFat);
		header2.getFont().setSize(16f);
		header2.setAlignment(Element.ALIGN_CENTER);
		document.add(header2);

		document.add(Chunk.NEWLINE);

		Paragraph header4 = new Paragraph("", fontTimesNewRomanFat);
		header4.getFont().setSize(12f);
		header4.add("«" + currentDay + "»");
		header4.add(" ");
		header4.add(currentMonth);
		header4.add(" ");
		header4.add("20" + currentYear);
		header4.add(" року");
		header4.setTabSettings(new TabSettings(440f));
		header4.add(Chunk.TABBING);

		header4.add(new Chunk("№" + currentDay + currentMonthNumber + currentYear + "/1"));
		document.add(header4);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		Paragraph header5 = new Paragraph("Витяг", fontTimesNewRomanFat);
		header5.getFont().setSize(22f);
		header5.setAlignment(Element.ALIGN_CENTER);
		document.add(header5);

		Paragraph header6 = new Paragraph("з Децентралізованого майнового реєстру", fontTimesNewRomanFat);
		header6.getFont().setSize(14f);
		header6.setAlignment(Element.ALIGN_CENTER);
		document.add(header6);

		Paragraph header7 = new Paragraph("природних ресурсів України", fontTimesNewRomanFat);
		header7.getFont().setSize(14f);
		header7.setAlignment(Element.ALIGN_CENTER);
		document.add(header7);

		document.add(Chunk.NEWLINE);

		Paragraph header8 = new Paragraph("", fontTimesNewRomanBase);
		header8.getFont().setSize(12f);
		header8.setAlignment(Element.ALIGN_JUSTIFIED);
		header8.add(new Chunk("Відомості надаються на підставі пункту 4 Положення про "
				+ "Децентралізований майновий реєстр природних ресурсів України, що "
				+ "затверджено Народним волевиявленням "));
		header8.add("№" + requestDay + requestMonthNumber + requestYear + "/1 від ");

		header8.add(requestDay);
		header8.add(".");
		header8.add(requestMonth);
		header8.add(".");
		header8.add("20" + requestYear + ".");
		header8.setTabSettings(new TabSettings(440f));

		header8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(header8);

		document.add(Chunk.NEWLINE);

		// Table
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		Paragraph th = new Paragraph("Найменування об’єкту", fontTimesNewRomanFat);
		th.getFont().setSize(11f);
		th.setAlignment(Element.ALIGN_LEFT);

		Paragraph td = new Paragraph(objectName, fontTimesNewRomanItalic);
		td.getFont().setSize(11f);
		td.setAlignment(Element.ALIGN_LEFT);

		PdfPCell cell1 = new PdfPCell(th);
		PdfPCell cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Клас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(objectClass, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Підклас об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(subObjectClass, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Власник об’єкту", fontTimesNewRomanFat);
		td = new Paragraph("народ України (Український народ)", fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Географічні координати кутів (вершин) об’єкту у форматі ГГ°ММ'СС,СС.",
				fontTimesNewRomanFat);
		cell1 = new PdfPCell(th);
		cell1.setMinimumHeight(150);
		table.addCell(cell1);

		// Neasted table
		PdfPTable nestedTable = new PdfPTable(4);
		Paragraph nth = new Paragraph("Північна широта", fontTimesNewRomanBase);
		nth.getFont().setSize(9);
		nth.setAlignment(Element.ALIGN_MIDDLE);
		nestedTable.addCell(nth);

		nth = new Paragraph("Східна довгота", fontTimesNewRomanBase);
		nestedTable.addCell(nth);

		nth = new Paragraph("Північна широта(продовження)", fontTimesNewRomanBase);
		nestedTable.addCell(nth);

		nth = new Paragraph("Східна довгота(продовження)", fontTimesNewRomanBase);
		nestedTable.addCell(nth);

		// empty rows

		for (Double d : totalCoordinates) {
			td = new Paragraph(String.format("%.4f", d), fontTimesNewRomanItalic);
			td.getFont().setSize(11f);
			nestedTable.addCell(td);
			// nestedTable.addCell(String.format("%.4f", d));

		}

		cell2 = new PdfPCell(nestedTable);
		table.addCell(cell2);
		// finish of neasted table

		th = new Paragraph("Лінійні розміри об’єкту, Д:Ш:В, м", fontTimesNewRomanFat);
		td = new Paragraph(linearObjectSize, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Загальна площа об’єкту, га", fontTimesNewRomanFat);
		td = new Paragraph(totalSquareOfObject, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Маса (вага) об’єкту, кг", fontTimesNewRomanFat);
		td = new Paragraph(weight, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Периметр об’єкту, м", fontTimesNewRomanFat);
		td = new Paragraph(objectPerimetr, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Об’єм об’єкту, м3", fontTimesNewRomanFat);
		td = new Paragraph(objectCapacity, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Підстава для внесення відомостей до Реєстру", fontTimesNewRomanFat);
		td = new Paragraph(reasonForInclusion, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("ПІБ та поштова адреса народного реєстратора", fontTimesNewRomanFat);
		td = new Paragraph(registratorLastName + " " + registratorFirtstName + " " + registratorMiddleName
				+ " 29000, м. Хмельницький, вул. Героїв Майдану, 17, кв. 17 ", fontTimesNewRomanItalic);
		td.add(Chunk.NEWLINE);
		td.add(registratorZipcode + ", м. " + registratorCity + ", вул. " + registratorStreetName + ", "
				+ registratorStreetNumber + ", кв. " + registratorHomeNumber);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Реєстраційний номер об’єкту", fontTimesNewRomanFat);
		td = new Paragraph(objectNumber, fontTimesNewRomanItalic);
		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		th = new Paragraph("Дата створення запису", fontTimesNewRomanFat);
		td = new Paragraph(requestDay + "." + requestMonthNumber + ".20" + requestYear + " року",
				fontTimesNewRomanItalic);

		cell1 = new PdfPCell(th);
		cell2 = new PdfPCell(td);
		table.addCell(cell1);
		table.addCell(cell2);

		document.add(table);

		document.add(Chunk.NEWLINE);

		Paragraph footer = new Paragraph("Народний реєстратор", fontTimesNewRomanFat);
		footer.getFont().setSize(12f);
		footer.setTabSettings(new TabSettings(370f));
		footer.add(Chunk.TABBING);

		char[] firstNameArray = registratorFirtstName.toCharArray();
		char[] middleNameArray = registratorMiddleName.toCharArray();
		String firstNameC = "" + firstNameArray[0];
		String middleNameC = "" + middleNameArray[0];
		String nameForFooter = registratorLastName + " " + firstNameC.toUpperCase() + ". " + middleNameC.toUpperCase()
				+ ".";

		footer.add(new Chunk("людина" + " " + nameForFooter));
		document.add(footer);

		document.close();
		return document;
	}

}
