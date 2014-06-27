package at.itprojekt.rules;

	import java.util.ArrayList;  
	  
	import javax.xml.bind.annotation.XmlElement;  
	import javax.xml.bind.annotation.XmlElementWrapper;  
	import javax.xml.bind.annotation.XmlRootElement;  
	import javax.xml.bind.annotation.XmlType;  
	 
	//define root element of XML file  
	@XmlRootElement  
	//@XmlType(propOrder = { "name", "yellow", "red", "weight"})  
	public class Rules {  
	  
		private ArrayList<Rule> listOfRules;  
		 
	 //private ArrayList<state> listOfStates;  
	 public Rules() {  
	    
	 }    
	 
	 //@XmlElement
	 public ArrayList<Rule> getListOfRules() {  
	  return listOfRules;  
	 }  
	    
	 @XmlElement(name = "rule")  
	 public void setListOfRules(ArrayList<Rule> listOfRules) {  
	  this.listOfRules = listOfRules;  
	 }
	   	
	}
