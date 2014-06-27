package at.itprojekt.rules;


	import java.io.File;  
import java.util.ArrayList;  

import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller;  
	  
	public class XmlToJava {  
	 public static void main(String[] args) {  
	  
	  try {  
	     
	   // create JAXB context and initializing Marshaller  
	   JAXBContext jaxbContext = JAXBContext.newInstance(Rules.class);  
	   
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
	     
	   // specify the location and name of xml file to be read  
	   String url = ClassLoader.getSystemClassLoader().getResource(".").getPath().substring(1);
	   File XMLfile = new File(url+"rules.xml");  
	     
	   // this will create Java object "rules" from the XML file  
	   Rules rules = (Rules) jaxbUnmarshaller.unmarshal(XMLfile);  
	   
	   // test output
	   System.out.println("red: "+rules.getRed()+" yellow: "+rules.getYellow()+" green: "+rules.getGreen());
	   ArrayList<Rule> listOfRules=rules.getListOfRules();  	    
	   int i=0;   
	   for(Rule r:listOfRules)  
	   {  
	    i++;  
	    System.out.println("Regel"+i+": "+r.getName()+" "+"red: "+r.getRed()+" yellow: "+r.getYellow()+" weight: "+r.getWeight());  
	   } 
	  
	  } catch (JAXBException e) {  
 
	   e.printStackTrace();  
	  }  
	  
	 }  
	}  

		
