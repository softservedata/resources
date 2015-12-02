package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class ApplShowAllRes {

	public static void main(String[] args) {
		
		List<ResourceDTO> listRes = new ArrayList<ResourceDTO>();
		RegistratorService rs = new RegistratorServiceImpl();
		listRes = rs.showAllResources();
		for(ResourceDTO r: listRes){
		System.out.println("\n\n\n" + "**************************************" + "\n" +
					"Ідентифікатор ресурсу: " + r.getIdentifier() + "\n"+
					"Тип ресурсу: " + r.getResourceType().getTypeName().toString() + "\n" +
					"Опис ресурсу: " + r.getDescription() + "\n" + 
					"Причина внесення в базу:  " + r.getReasonInclusion() + "\n" +
					"Ім'я та прізвище реєстратора   " + r.getRegistratorName()  + "\n"+
					"Номер тому: " + r.getTomeIdentifier() + "\n"+
					"Дата внесення ресурсу: " + r.getDate() + "\n");
		}
}
}

