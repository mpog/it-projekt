package at.itprojekt.rules;

	import java.util.ArrayList;  
	  
	import javax.xml.bind.annotation.XmlElement;  
	import javax.xml.bind.annotation.XmlElementWrapper;  
	import javax.xml.bind.annotation.XmlRootElement;  
	import javax.xml.bind.annotation.XmlType;  
	  
	public class Rule {  
	  
	 private String name;  
	 private double yellow;
	 private double red;
	 private double weight;
	        
	 public Rule() {  
	    
	 }  
	 public String getName() {  
	  return name;  
	 }  
	 @XmlElement  
	 public void setName(String name) {  
	  this.name = name;  
	 }
	 
	 public double getYellow() {  
	  return yellow;  
	 }    
	 @XmlElement  
	 public void setYellow(double yellow) {  
	  this.yellow = yellow;  
	 }  
	 
	 public double getRed() {  
		  return red;  
		 }  	
	 @XmlElement  
	 public void setRed(double red) {  
	  this.red = red;  
	 }  
	 
	 public double getWeight() {  
		  return weight;  
	 }  			   
	 @XmlElement  
	 public void setWeight(double weight) {  
	  this.weight = weight;  
		 }  		 
	   	
	}
