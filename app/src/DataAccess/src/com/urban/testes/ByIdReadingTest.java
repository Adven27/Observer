package com.urban.testes;

import com.example.prototype.dao.DAO;
import com.example.prototype.dao.DefaultDaoInitializer;
import com.example.prototype.dao.JDBCSLQLightDao;
import com.urban.entity.Address;
import com.urban.entity.Advertising;
import com.urban.entity.Category;
import com.urban.entity.Contact;
import com.urban.entity.Event;
import com.urban.entity.Organization;
import com.urban.entity.Place;
import com.urban.entity.Position;

public class ByIdReadingTest {

	public static void main(String[] args) {
	   DefaultDaoInitializer.setDaoImpl(new JDBCSLQLightDao("jdbc:sqlite:..\\Prototype\\assets\\urban.db"));
       Category category = DAO.get(Category.class, 1);
       System.out.println(category != null ? category.getName() : null);
       System.out.println("Pos count:" + category.getPositions().size());
       for (Position pos: category.getPositions()){
    	   System.out.println("Position:" + pos.getName()); 
       }
       
       Advertising adv = DAO.get(Advertising.class, 2);
       System.out.println("Advertising");
       System.out.println(adv.getText());
       System.out.println("Img size:" + adv.getImage().getAsBinary().length);
       System.out.println("Org:" + adv.getPosition().getName());
       System.out.println("Img size:" + adv.getPosition().getOrganization().getLogo().getAsBinary().length);  
       
       Organization org = DAO.get(Organization.class, 1);
       
       System.out.println("Organization");
       System.out.println(org.getName());
       
       System.out.println("Contacts.");
       for (Contact cont : org.getContacts()){
           System.out.print("ContType:" +  cont.getType());
           System.out.println(" ContInfo:" + cont.getInfo());
       }
       System.out.println(); 
       
       System.out.println("Places.");
       for (Place place : org.getPlaces()){
           System.out.print("Alt: " +  place.getAlt());
           System.out.println(" Lat: " + place.getLat());
       }
       System.out.println(); 
       
       System.out.println("Addreses.");
       for (Address addr : org.getPlaces().iterator().next().getAddresses()){
           System.out.print("Street: " +  addr.getStreet());
           System.out.println(" Floor:" + addr.getFloor());
       }
       System.out.println(); 
       
       System.out.println("Events.");
       Event event = org.getEvents().iterator().next();
       
       System.out.println("Event: " +  event.getText());
       System.out.println("Event place: " +  event.getPlaces().iterator().next().getDescription());
       System.out.println(); 

	}

}
