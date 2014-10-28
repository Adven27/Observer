package com.example.prototype.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.urban.entity.Address;
import com.urban.entity.Advertising;
import com.urban.entity.Category;
import com.urban.entity.Contact;
import com.urban.entity.Event;
import com.urban.entity.Image;
import com.urban.entity.Organization;
import com.urban.entity.Place;
import com.urban.entity.Position;
import com.urban.entity.pojo.AddressPojo;
import com.urban.entity.pojo.AdvertisingPojo;
import com.urban.entity.pojo.CategoryPojo;
import com.urban.entity.pojo.ContactPojo;
import com.urban.entity.pojo.EventPojo;
import com.urban.entity.pojo.ImagePojo;
import com.urban.entity.pojo.OrganizationPojo;
import com.urban.entity.pojo.PlacePojo;
import com.urban.entity.pojo.PositionPojo;

public class DAO {

   private static Map<Class, Class> classes = new HashMap<Class, Class>();
   static{
       classes.put(Category.class, CategoryPojo.class);
       classes.put(Position.class, PositionPojo.class);
       classes.put(Image.class, ImagePojo.class);
       classes.put(Organization.class, OrganizationPojo.class);
       classes.put(Advertising.class, AdvertisingPojo.class);
       classes.put(Place.class, PlacePojo.class);
       classes.put(Address.class, AddressPojo.class);
       classes.put(Event.class, EventPojo.class);
       classes.put(Contact.class, ContactPojo.class);
   }

   static void setDAO(IDAO dao){
       curDao = dao;
   }

   private static IDAO curDao ;//= new JDBCSLQLightDao("jdbc:sqlite:..\\Prototype\\assets\\urban.db");

   public static void save(Object objToSave) throws SQLException {
       curDao.save(objToSave);
   }

   public static <T> Collection<T> getAll(Class<T> type){
      return  classes.containsKey(type) ?
           curDao.getAll(classes.get(type)) : curDao.getAll(type);
   }

   public static <T> T get(Class<T> type, long id){
       return  (T) (classes.containsKey(type) ?
               curDao.get(classes.get(type), id) : curDao.get(type, id));
   }

}
