package org.registrator.community.service.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.User;
import org.registrator.community.entity.UserStatus;
import org.registrator.community.service.interfaces.AdminService;
import org.registrator.community.service.interfaces.SearchService;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/*
 * This class is defined in order to describe the bacis 
 * operation that can perfom administrator of our web site
 */

public class AdminServiceImpl implements AdminService, SearchService {

	private XWPFDocument document;
	private FileOutputStream out;
	private XWPFParagraph paragraph;
	private Document documentPDF;
	private FileOutputStream outPDF;
	private PdfWriter writerPDF;

	public AdminServiceImpl() {

		try {
			document = new XWPFDocument();
			out = new FileOutputStream(new File("AllUsers.docx"));
			paragraph = document.createParagraph();

			documentPDF = new Document();
			outPDF = new FileOutputStream("AllUsers.pdf");
			writerPDF=PdfWriter.getInstance(documentPDF, outPDF);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public void saveAllUsersToPDF() throws DocumentException{
		documentPDF.open();
		Paragraph paragraph=new Paragraph();
		paragraph.add(getAllUsers().toString());
		documentPDF.add(paragraph);
		documentPDF.close();
	}
	
	
	
	public void saveAllUsersToWord() throws IOException {
		paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
		paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
		paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
		paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);

		XWPFRun run = paragraph.createRun();

		run.setText(getAllUsers().toString());

		document.write(out);
		out.close();
		System.out.println("Data sucsessfully saved to 'AllUsers.docx' document");
	}

	// Method for receive all Users from data base
	@Override
	public List<UserDTO> getAllUsers() {

		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<User> userList = DaoFactory.get().getUserDao().getAll();

		List<PassportDTO> passportDtoList = getPassportDto();
		List<AddressDTO> addressDtoList = getAddressDto();

		int i = 0;
		for (User userEntity : userList) {
			UserDTO userDto = new UserDTO();
			userDto.setAddress(addressDtoList.get(i));
			userDto.setEmail(userEntity.getEmail());
			userDto.setFirstName(userEntity.getFirstName());
			userDto.setLastName(userEntity.getLastName());
			userDto.setLogin(userEntity.getLogin());
			userDto.setMiddleName(userEntity.getMiddleName());
			userDto.setPassport(passportDtoList.get(i));
			userDto.setPassword(userEntity.getPassword());
			userDto.setRole(userEntity.getRole());
			userDto.setStatus(userEntity.getStatus().toString());
			userDtoList.add(userDto);
			i++;

		}

		return userDtoList;
	}

	// Method for receive passport information from data base
	private List<PassportDTO> getPassportDto() {
		List<PassportInfo> passportInfoList = DaoFactory.get().getPassportInfoDao().getAll();
		List<PassportDTO> passportDtoList = new ArrayList<PassportDTO>();

		for (PassportInfo passportEntity : passportInfoList) {
			PassportDTO passportDto = new PassportDTO();
			passportDto.setNumber(passportEntity.getNumber());
			passportDto.setPublished_by_data(passportEntity.getPublished_by_data());
			passportDto.setSeria(passportEntity.getSeria());
			passportDtoList.add(passportDto);
		}

		return passportDtoList;

	}

	// Method for receive address information from data base
	private List<AddressDTO> getAddressDto() {
		List<AddressDTO> addressDtoList = new ArrayList<AddressDTO>();
		List<Address> addressList = DaoFactory.get().getAddressDao().getAll();

		for (Address addressEntity : addressList) {
			AddressDTO addressDto = new AddressDTO();
			addressDto.setBuilding(addressEntity.getBuilding());
			addressDto.setCity(addressEntity.getCity());
			addressDto.setDistrict(addressEntity.getDistrict());
			addressDto.setFlat(addressEntity.getFlat());
			addressDto.setPostcode(addressEntity.getPostCode());
			addressDto.setRegion(addressEntity.getRegion());
			addressDto.setStreet(addressEntity.getStreet());
			addressDtoList.add(addressDto);
		}

		return addressDtoList;

	}

	// Method for change user status
	@Override
	public String changeUserStatus(String login) {

		User user = DaoFactory.get().getUserDao().findByLogin(login);

		if (user.getStatus() == UserStatus.UNBLOCK) {
			user.setStatus(UserStatus.BLOCK);
			DaoFactory.get().getUserDao().update(user);
			return UserStatus.BLOCK.toString();
		} else {
			user.setStatus(UserStatus.UNBLOCK);
			DaoFactory.get().getUserDao().update(user);
			return UserStatus.UNBLOCK.toString();
		}
	}

	// Method for change role
	@SuppressWarnings("resource")
	@Override
	public Role changeRole(String login) {
		User user = DaoFactory.get().getUserDao().findByLogin(login);
		Scanner sc = new Scanner(System.in);
		List<Role> roleList = DaoFactory.getDaoFactory().getRoleDao().getAll();

		String roleName = user.getRole().getName().toString();
		int key = 0;

		System.out.println("User role " + roleName);

		if (roleName == "USER") {
			System.out.println("Press 1 for changing USER to REGISTRATOR or Press 2 for changing USER to ADMIN");
			key = sc.nextInt();
			if (key == 1) {
				user.setRole(roleList.get(1));
				DaoFactory.get().getUserDao().update(user);
				return roleList.get(1);
			} else {
				if (key == 2) {
					user.setRole(roleList.get(0));
					DaoFactory.get().getUserDao().update(user);
					return roleList.get(0);
				}
			}
		} else {
			if (roleName == "REGISTRATOR") {
				System.out.println(
						"Press 1 for changing REGISTRATOR to USER or Press 2 for changing REGISTRATOR to ADMIN");
				key = sc.nextInt();
				if (key == 1) {
					user.setRole(roleList.get(2));
					DaoFactory.get().getUserDao().update(user);
					return roleList.get(2);
				} else {
					if (key == 2) {
						user.setRole(roleList.get(0));
						DaoFactory.get().getUserDao().update(user);
						return roleList.get(0);
					}
				}
			} else {
				if (roleName == "ADMIN") {
					System.out
							.println("Press 1 for changing ADMIN to USER or Press 2 for changing ADMIN to REGISTRAROR");
					key = sc.nextInt();
					if (key == 1) {
						user.setRole(roleList.get(2));
						DaoFactory.get().getUserDao().update(user);
						return roleList.get(2);
					} else {
						if (key == 2) {
							user.setRole(roleList.get(1));
							DaoFactory.get().getUserDao().update(user);
							return roleList.get(1);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void showAllUsers(List<UserDTO> userDtoList) {

		for (UserDTO userDto : userDtoList) {
			System.out.println(userDto);
			System.out.println();
		}
	}
}