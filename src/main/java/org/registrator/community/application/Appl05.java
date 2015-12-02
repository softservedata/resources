package org.registrator.community.application;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class Appl05 {
public static void main(String[] args) {
	ResourceDTO res = new ResourceDTO();
	RegistratorService rs = new RegistratorServiceImpl();
	rs.showResourceByIdentifier("1111");
	System.out.println("\n\n\n" + "**************************************" + "\n" +
			"Ідентифікатор ресурсу: " + res.getIdentifier() + "\n"+
			"Тип ресурсу: " + res.getResourceType().getTypeName().toString() + "\n" +
			"Опис ресурсу: " + res.getDescription() + "\n" + 
			"Причина внесення в базу:  " + res.getReasonInclusion() + "\n" +
			"Ім'я та прізвище реєстратора   " + res.getRegistratorName()  + "\n"+
			"Номер тому: " + res.getTomeIdentifier() + "\n"+
			"Дата внесення ресурсу: " + res.getDate() + "\n");
}
}
