package org.registrator.community.controller.administrator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *Class controller works with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 *@author Ann
 *
 */
@Controller
@RequestMapping(value ="/inquiry/add/")
public class InquiryController {
	
	@Autowired
	private Logger logger;	
	@Autowired
	private InquiryService inquiryService;					
	@Autowired
	private PrintService printService;
	@Autowired
	private UserService userService;
	 
	
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate aboute the resource 
	 * (with existing registrators and resources).	
	 *  
	 * @param model - the model
	 * @return inquiryAddOut.jsp
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/outputInquiry", method = RequestMethod.GET)
	public String showOutputInquiry(Model model) {		
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("userLogin = " + userLogin);
		List<UserNameDTO> listUserNameDTO = inquiryService.listUserNameDTO(userLogin);				
		model.addAttribute("registrators", listUserNameDTO);		
		return "inquiryAddOut";
	}
	

	
	/**
	 * Method saves the data in the table inquiry_list.
	 *
	 * @param resourceIdentifier - identifier of the resource.
	 * @param registratorLogin - login of chosen registrator.
	 * @return listInqUserOut.jsp
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/addOutputInquiry", method = RequestMethod.POST)
	public String addOutputInquiry(String resourceIdentifier, String registratorLogin) {  	
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("userLogin = " + userLogin);
		inquiryService.addOutputInquiry(resourceIdentifier, registratorLogin, userLogin);		
		return  "redirect:/inquiry/add/listInqUserOut";	
	}
	
	/**
	 * Method for showing all output inquiries from logged user on UI.
	 * 
	 * @param model - the model
	 * @return listInqUserOut.jsp
	 */
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_COMMISSIONER')")
	@RequestMapping(value = "/listInqUserOut", method = RequestMethod.GET)	
	public String listInqUserOut(Model model) {					
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		String role = userService.getUserByLogin(userLogin).getRole().getType().toString();
		logger.info("user role = " + role);
		List<InquiryListDTO> listInquiryUserOut = inquiryService.listInquiryUser(userLogin, InquiryType.OUTPUT);
		model.addAttribute("listInquiryUserOut", listInquiryUserOut);
		model.addAttribute("role", role);		
		return "listInqUserOut";
	}
	
	/**
	 * Method for showing all input inquiries from logged user on UI.
	 * 
	 * @param model - the model
	 * @return listInquiryUserInput.jsp
	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER') or hasRole('ROLE_COMMISSIONER')")
	@RequestMapping(value = "/listInquiryUserInput", method = RequestMethod.GET)
	public String listInquiryUserInput(Model model) {
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		List<InquiryListDTO> listInquiryUserInput = inquiryService.listInquiryUser(userLogin, InquiryType.INPUT);
		model.addAttribute("listInquiryUser", listInquiryUserInput);
		return "listInquiryUserInput";
	}
	
	/**
	 * Method for deleting chosen inquiry by Id.
	 * 
	 * @param inquiryId - inquiry identifier.
	 * @return listInqUserOut.jsp
	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/delete/{inquiryId}")
	public String deleteInquiry(@PathVariable Integer inquiryId) {		
		inquiryService.removeInquiry(inquiryId);
		return "redirect:/inquiry/add/listInqUserOut";
	}
	
	  
 	
    /**
 	 * @author Vitalii Horban
 	 * generate pdf document "mandate to extract" on button pressing and open this document in the same inset
 	 */

    
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
 	@RequestMapping(value = "/printOutput/{inquiryId}", method = RequestMethod.GET)
 	public void downloadFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printProcuration(inquiryId);

 		File file = null;
 		file = new File("D:\\mandateToExtract" + inquiryId + ".pdf");

 		if (!file.exists()) {
 			String errorMessage = "Sorry. The file you are looking for does not exist";
 			System.out.println(errorMessage);
 			OutputStream outputStream = response.getOutputStream();
 			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
 			outputStream.close();
 			return;
 		}

 		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
 		if (mimeType == null) {
 			System.out.println("mimetype is not detectable, will take default");
 			mimeType = "application/octet-stream";
 		}

 		System.out.println("mimetype : " + mimeType);

 		response.setContentType(mimeType);

 		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
 		response.setContentLength((int) file.length());
 		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 		FileCopyUtils.copy(inputStream, response.getOutputStream());
 	}
 	
 	
 	
	/**
 	 * @author Vitalii Horban
 	 * generate pdf document "extract" on button pressing and open this document in the same inset
 	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
 	@RequestMapping(value = "/printExtract/{inquiryId}", method = RequestMethod.GET)
 	public void downloadExtractFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printExtract(inquiryId);

 		File file = null;

 		file = new File("D:\\extract" + inquiryId + ".pdf");

 		if (!file.exists()) {
 			String errorMessage = "Sorry. The file you are looking for does not exist";
 			System.out.println(errorMessage);
 			OutputStream outputStream = response.getOutputStream();
 			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
 			outputStream.close();
 			return;
 		}

 		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
 		if (mimeType == null) {
 			System.out.println("mimetype is not detectable, will take default");
 			mimeType = "application/octet-stream";
 		}

 		System.out.println("mimetype : " + mimeType);

 		response.setContentType(mimeType);

 		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
 		response.setContentLength((int) file.length());
 		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 		FileCopyUtils.copy(inputStream, response.getOutputStream());
 	}
 	
 	
 	
 	
 	
 	/**
 	 * @author Vitalii Horban
 	 * generate pdf document "ProcurationOnSubmit" on button pressing and open this document in the same inset
 	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR')")
 	@RequestMapping(value = "/printdata/{inquiryId}", method = RequestMethod.GET)
 	public void downloadInfoFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printProcurationOnSubmitInfo(inquiryId);

 		File file = null;

 		file = new File("D:\\mandateToInput" + inquiryId + ".pdf");

 		if (!file.exists()) {
 			String errorMessage = "Sorry. The file you are looking for does not exist";
 			System.out.println(errorMessage);
 			OutputStream outputStream = response.getOutputStream();
 			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
 			outputStream.close();
 			return;
 		}

 		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
 		if (mimeType == null) {
 			System.out.println("mimetype is not detectable, will take default");
 			mimeType = "application/octet-stream";
 		}

 		System.out.println("mimetype : " + mimeType);

 		response.setContentType(mimeType);

 		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
 		response.setContentLength((int) file.length());
 		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 		FileCopyUtils.copy(inputStream, response.getOutputStream());
 	}
 	
 	
}    

