package org.registrator.community.controller.administrator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.InquiryDTO;
import org.registrator.community.dto.InquiryListDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.dto.UserNameDTO;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.InquiryType;
import org.registrator.community.service.DiscreteParameterService;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.LinearParameterService;
import org.registrator.community.service.PrintService;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;


/**
 *Class controller works with procurations of entering data into the register
 *(input inquiry) and with procurations for an extract from register (output inquiry).
 *@author Ann
 *
 */
@Controller
@RequestMapping(value ="/inquiry/add/")
public class InquiryController {
	//public static final Logger logger = LoggerFactory.getLogger(InquiryController.class);
	@Autowired
	Logger logger;	
	@Autowired
	InquiryService inquiryService;		
	@Autowired
	ResourceRepository resourceRepository;	
	@Autowired
	ResourceTypeService resourceTypeService;	
	@Autowired
	ResourceService resourceService;	
	@Autowired
	DiscreteParameterService discreteParameterService;	
	@Autowired
	LinearParameterService linearParameterService;	
	@Autowired
	PrintService printService;
	@Autowired
	UserService userService;
	 
	
	/**
	 * Method for showing form on UI to input the parameters 
	 * for inquiry to get the certificate aboute the resource 
	 * (with existing registrators and resources).	
	 *  
	 * @param model - the model
	 * @return inquiryAddOut.jsp
	 */
	@RequestMapping(value = "/outputInquiry", method = RequestMethod.POST)
	public String showOutputInquiry(Model model) {
		logger.info("begin showOutputInquiry");
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("userLogin = " + userLogin);
		List<UserNameDTO> listUserNameDTO = inquiryService.listUserNameDTO(userLogin);				
		model.addAttribute("registrators", listUserNameDTO);		
		logger.info(listUserNameDTO.toString());
		logger.info("end showOutputInquiry");
		return "inquiryAddOut";
	}
	

	
	/**
	 * Method saves the data in the table inquiry_list.
	 *
	 * @param resourceIdentifier - identifier of the resource.
	 * @param registratorLogin - login of chosen registrator.
	 * @return listInqUserOut.jsp
	 */
	@RequestMapping(value = "/addOutputInquiry", method = RequestMethod.POST)
	public String addOutputInquiry(String resourceIdentifier, String registratorLogin) {  			
		logger.info("begin addOutputInquiry, param resourceIdentifier = " + resourceIdentifier +
				", registratorLogin = "+ registratorLogin);		
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("userLogin = " + userLogin);
		inquiryService.addOutputInquiry(resourceIdentifier, registratorLogin, userLogin);
		logger.info("end addOutputInquiry");
		return  "redirect:/inquiry/add/listInqUserOut";	
	}
	
	/**
	 * Method for showing all output inquiries from logged user on UI.
	 * 
	 * @param model - the model
	 * @return listInqUserOut.jsp
	 */
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REGISTRATOR')")
	@RequestMapping(value = "/listInqUserOut", method = RequestMethod.GET)	
	public String listInqUserOut(Model model) {	
		logger.info("begin listInqUserOut");		
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		String role = userService.getUserByLogin(userLogin).getRole().getType().toString();
		logger.info("user role = " + role);
		List<InquiryListDTO> listInquiryUserOut = inquiryService.listInquiryUser(userLogin, InquiryType.OUTPUT);
		model.addAttribute("listInquiryUserOut", listInquiryUserOut);
		model.addAttribute("role", role);
		logger.info("end listInqUserOut");
		return "listInqUserOut";
	}
	
	/**
	 * Method for showing all input inquiries from logged user on UI.
	 * 
	 * @param model - the model
	 * @return listInquiryUserInput.jsp
	 */
	@PreAuthorize("hasRole('ROLE_REGISTRATOR') or hasRole('ROLE_USER')")
	@RequestMapping(value = "/listInquiryUserInput", method = RequestMethod.GET)
	public String listInquiryUserInput(Model model) {
		logger.info("begin listInquiryUserInput");
		String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
		List<InquiryListDTO> listInquiryUserInput = inquiryService.listInquiryUser(userLogin, InquiryType.INPUT);
		model.addAttribute("listInquiryUser", listInquiryUserInput);
		logger.info("end listInquiryUserInput");
		return "listInquiryUserInput";
	}
	
	/**
	 * Method for deleting chosen inquiry by Id.
	 * 
	 * @param inquiryId - inquiry identifier.
	 * @return listInqUserOut.jsp
	 */
	@RequestMapping(value = "/delete/{inquiryId}")
	public String deleteInquiry(@PathVariable Integer inquiryId) {
		logger.info("begin deleteInquiry, param inquiryId = " + inquiryId);
		inquiryService.removeInquiry(inquiryId);
		logger.info("end deleteInquiry");
		return "redirect:/inquiry/add/listInqUserOut";
	}
	
	
	/**
     * Show the information about resource by identifier
     * !copy from ResourceController
     * 
     * @param identifier - resource identifier.
	 * @return showResource.jsp
     */
    @RequestMapping(value = "/get/{identifier}", method = RequestMethod.GET)
    public String getResourceByIdentifier(@PathVariable("identifier") String identifier, Model model) {
    	logger.info("begin getResourceByIdentifier, param = " + identifier);
        ResourceDTO resourceDTO = resourceService.findByIdentifier(identifier);
        model.addAttribute("resource", resourceDTO);
        logger.info("end getResourceByIdentifier");
        return "showResource";
    }

    
 	
    /**
 	 * @author Vitalii Horban
 	 * generate pdf document "mandate to extract" on button pressing and open this document in the same inset
 	 */

    
    
 	@RequestMapping(value = "/printOutput/{inquiryId}", method = RequestMethod.GET)
 	public void downloadFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printProcuration(inquiryId);

 		File file = null;
 		file = new File("D:\\file.pdf");

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
 	
 	@RequestMapping(value = "/printExtract/{inquiryId}", method = RequestMethod.GET)
 	public void downloadExtractFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printExtract(inquiryId);

 		File file = null;

 		file = new File("D:\\file.pdf");

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
 	
 	@RequestMapping(value = "/printdata/{inquiryId}", method = RequestMethod.GET)
 	public void downloadInfoFile(HttpServletResponse response, @PathVariable("inquiryId") Integer inquiryId)
 			throws IOException {

 		printService.printProcurationOnSubmitInfo(inquiryId);

 		File file = null;

 		file = new File("D:\\file.pdf");

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

// !!! user can't add resource!!!
//	/**
//     * Method for loading form for input the parameter of resource (with
//     * existing resource types and registrator)
//     * !copy from ResourceController
//     */
//		@RequestMapping(value = "/addresource", method = RequestMethod.GET)
//	public String addResourceForm(Model model) {
//		List<ResourceType> listOfResourceType = resourceTypeService.findAll();
//		List<Tome> tomes = tomeRepository.findAll();
//		ResourceDTO newresource = new ResourceDTO();
//		model.addAttribute("listOfResourceType", listOfResourceType);
//		model.addAttribute("tomes", tomes);
//		model.addAttribute("newresource", newresource);
//		return "addResource";
//	}
//	
//		/** 
//	     * !copy from ResourceController
//	     */
//	@RequestMapping(value = "/getParameters", method = RequestMethod.POST)
//	public String add(@RequestParam("resourceTypeName") String typeName, Model model) {
//		ResourceType resType = resourceTypeService.findByName(typeName);
//		
//		List<DiscreteParameter> discreteParameters = discreteParameterService.findAllByResourceType(resType);
//        List<LinearParameter> linearParameters = linearParameterService.findAllByResourceType(resType);
//
//        model.addAttribute("discreteParameters", discreteParameters);
//        model.addAttribute("linearParameters", linearParameters);
//		return "resourceValues";
//	}
//	
//	/**
//     * Method save the resource in table list_of resources
//     * similar to ResourceController
//     */
//    @RequestMapping(value = "/addresource", method = RequestMethod.POST)
//    public String addResource(@Valid @ModelAttribute("newresource") ResourceDTO resourceDTO,
//                              BindingResult result, Model model, HttpSession session) {
//    	//String userLogin =(String) session.getAttribute("userLogin");
//    	String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
//    	resourceDTO = inquiryService.addInputInquiry(resourceDTO, userLogin);
//         model.addAttribute("resource", resourceDTO);
//         return "showResource";
//    }
//	
//  

/*@RequestMapping(value = "/outputInquiry", method = RequestMethod.GET)
	public String showOutputInquiry(Model model) {
	logger.info("begin");
	List<TomeDTO>  listTomeDTO = inquiryService.listTomeDTO();
	model.addAttribute("tomes", listTomeDTO);
	Iterable<Resource> resources = resourceRepository.findAll();
	model.addAttribute("resources", resources);
	logger.info("end");
	return "inquiryAddOut";
}*/
