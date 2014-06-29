package at.itprojekt.rules;

	import java.util.ArrayList;  
	  

	

	import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlElementWrapper;  
import javax.xml.bind.annotation.XmlRootElement;  
import javax.xml.bind.annotation.XmlType;  
		  
	public class Rule {  
	  
	 private ArrayList<Test> listOfTests;
	 private int id;
	 private double weight;
	 private String type;
	        
	 public Rule() {  
	    
	 }  
	 public int getId() {  
	  return id;  
	 }  
	 @XmlAttribute
	 public void setId(int id) {  
	  this.id = id;  
	 }
	 
/*	 public double getYellow() {  
	  return yellow;  
	 }    
	 @XmlAttribute 
	 public void setYellow(double yellow) {  
	  this.yellow = yellow;  
	 }  
	 
	 public double getRed() {  
		  return red;  
		 }  	
	 @XmlAttribute
	 public void setRed(double red) {  
	  this.red = red;  
	 }  
	 */
	 
	 public String getType() {  
		  return type;  
		 }  	
	 @XmlAttribute
	 public void setType(String type) {  
	  this.type = type;  
	 } 
	 
	 public double getWeight() {  
		  return weight;  
	 }  			   
	 @XmlAttribute
	 public void setWeight(double weight) {  
	  this.weight = weight;  
		 }  		 
	   	
	 public ArrayList<Test> getListOfTests() {  
	  return listOfTests;  
	 }  
	    
	 @XmlElement(name = "test")  
	 public void setListOfTests(ArrayList<Test> listOfTests) {  
	  this.listOfTests = listOfTests;  
	 }
	 
	}
