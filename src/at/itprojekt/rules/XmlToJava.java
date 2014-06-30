package at.itprojekt.rules;


import java.io.File;  
import java.util.ArrayList;  

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller;  
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlToJava {  
	public static void main(String[] args) {  

		Rules rules = generate();
		
		// test output
		System.out.println("red: "+rules.getRed()+" yellow: "+rules.getYellow()+" green: "+rules.getGreen());
		ArrayList<Rule> listOfRules=rules.getListOfRules();  	    
		int i=0;   
		for(Rule r:listOfRules)  
		{  
			i++;  
			System.out.println("Regel"+i+": "+r.getId()+" "+r.getType()+" weight: "+r.getWeight());
			ArrayList<Test> listOfTests=r.getListOfTests();
			for(Test t:listOfTests)  
			{  
				System.out.println("- "+t.getValue());
			}
		} 

	}  

	public static Rules generate(){
		
		Rules rules = new Rules();
		try {  
		// create JAXB context and initializing Marshaller  
		JAXBContext jaxbContext = JAXBContext.newInstance(Rules.class);  

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  

		// specify the location and name of xml file to be read  
		String url = ClassLoader.getSystemClassLoader().getResource(".").getPath().substring(1);
		File XMLfile = new File(url+"rules.xml");  
		File XSDfile = new File(url+"rules.xsd");

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(XSDfile);
        jaxbUnmarshaller.setSchema(schema);
        jaxbUnmarshaller.setEventHandler(new MyValidationEventHandler());
        
		// this will create Java object "rules" from the XML file  
        jaxbUnmarshaller.unmarshal(new InputSource(url+"rules.xml"));
		rules = (Rules) jaxbUnmarshaller.unmarshal(XMLfile);
		     
        
	} catch (JAXBException e) {  

		e.printStackTrace();  
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return rules;
	}
	
	private static class MyValidationEventHandler implements ValidationEventHandler {

        public boolean handleEvent(ValidationEvent arg0) {
            System.out.println(arg0.getSeverity());
            return true;
        }

    }
	
}



