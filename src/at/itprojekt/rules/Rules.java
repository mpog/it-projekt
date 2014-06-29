package at.itprojekt.rules;

	import java.util.ArrayList;  
	  

	import javax.xml.bind.annotation.XmlElement;  
	import javax.xml.bind.annotation.XmlElementWrapper;  
	import javax.xml.bind.annotation.XmlRootElement;  
	import javax.xml.bind.annotation.XmlType;  
	import javax.xml.bind.annotation.XmlAttribute;
	 
	//define root element of XML file  
	@XmlRootElement  
	//@XmlType(propOrder = { "name", "yellow", "red", "weight"})  
	public class Rules {  
	  
		private ArrayList<Rule> listOfRules;  
		private double red;
		private double yellow;
		private double green;
		 
	 //private ArrayList<state> listOfStates;  
	 public Rules() {  
	    
	 }    
	 
	 public double getRed() {  
		  return red;  
		 }  
	 @XmlAttribute  
	 public void setRed(double red) {  
	  this.red = red;  
	 }
	 
	 public double getYellow() {  
		  return yellow;  
		 }  
	 @XmlAttribute  
	 public void setYellow(double yellow) {  
	  this.yellow = yellow;  
	 }
	 
	 public double getGreen() {  
		  return green;  
		 }  
	 @XmlAttribute  
	 public void setGreen(double green) {  
	  this.green = green;  
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
