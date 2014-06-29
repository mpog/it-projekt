package at.itprojekt.rules;

	import java.util.ArrayList;  
	  

	import javax.xml.bind.annotation.XmlAttribute;
	import javax.xml.bind.annotation.XmlElement;  
	import javax.xml.bind.annotation.XmlElementWrapper;  
	import javax.xml.bind.annotation.XmlRootElement;  
	import javax.xml.bind.annotation.XmlType;  
	  
	public class Test {  
	  
	 private String value;  
	 private double yellow;
	 private double red;
	 private double weight;
	        
	 public Test() {  
	    
	 }  
	 public String getValue() {  
	  return value;  
	 }  
	 @XmlAttribute  
	 public void setValue(String value) {  
	  this.value = value;  
	 }
	 
	 public double getYellow() {  
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
	 
	 public double getWeight() {  
		  return weight;  
	 }  			   
	 @XmlAttribute
	 public void setWeight(double weight) {  
	  this.weight = weight;  
		 }  		 
	   	
	}
