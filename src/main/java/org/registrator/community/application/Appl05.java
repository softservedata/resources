package org.registrator.community.application;

import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class Appl05 {
public static void main(String[] args) {
   RegistratorService rs = new RegistratorServiceImpl();
   System.out.println(rs.showResourceByIdentifier("123555"));
}
}
