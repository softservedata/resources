package org.registrator.community.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

public class XmlAnalyzerUtil {
	
	public <T> void marshal(Class<T> clazz, T object, String filePath){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(object, new FileOutputStream(filePath));
			marshaller.marshal(object, System.out);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T unmarshal(Class<T> clazz,String filePath){
		try{
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (T)  unmarshaller.unmarshal(getClass().getClassLoader()
		            .getResourceAsStream(filePath));
			
		} catch (JAXBException e) {
			e.printStackTrace();
		} 	
		return null;
	}
	
	public boolean isDocumentValib(String fileName,String schemaName){
		String lanquage = XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI;
		SchemaFactory schemaFactory = SchemaFactory.newInstance(lanquage) ;
		return false;
	}
}
