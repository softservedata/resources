package org.registrator.community.application;

import java.io.IOException;

import org.registrator.community.service.implementation.AdminServiceImpl;
import org.registrator.community.service.implementation.RegistratorServiceImpl;

import com.itextpdf.text.DocumentException;

public class Demo1 {

	public static void main(String[] args) throws IOException, DocumentException {

		// Creating tables and filling in it
		CreateTableInDB create = new CreateTableInDB();
//		 create.addSeveralUsers();
		 create.createTables();

		AdminServiceImpl admin = new AdminServiceImpl();
		/* save all users to word file */
		// admin.saveAllUsersToWord();

		/* save all users to PDF file */
		// admin.saveAllUsersToPDF();

		RegistratorServiceImpl registrator = new RegistratorServiceImpl();
		/* save all resources to word file */
		// registrator.saveAllResourcesToWord();

		/* save all resources to PDF file */
		// registrator.saveAllResourcesToPDF();

		/* save all resource types to word */
		// registrator.saveAllResourceTypesToWord();

		/* save all resource types to PDF */
		// registrator.saveAllResourceTypesToPDF();

	}

}
