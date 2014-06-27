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
	     
	   // this will create Java object "rule" from the XML file  
	   Rules rule = (Rules) jaxbUnmarshaller.unmarshal(XMLfile);  
	   
	   // test output
	   ArrayList<Rule> listOfRules=rule.getListOfRules();  	    
	   int i=0;   
	   for(Rule r:listOfRules)  
	   {  
	    i++;  
	    System.out.println("Regel"+i+": "+r.getName()+" "+r.getRed()+" "+r.getYellow()+" "+r.getWeight());  
	   } 
	  
	  } catch (JAXBException e) {  
 
	   e.printStackTrace();  
	  }  
	  
	 }  
	}  

		
