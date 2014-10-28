package com.urban.entity;

import java.util.HashMap;
import java.util.Map;

public enum ContactType {
    PHONE(1, "Телефон"), EMAIL(2, "e-mail"), WEB(3, "Сайт"), SKYPE(4, "Skype");

    private static Map<Long, ContactType> searchMap = new HashMap<Long, ContactType>();

    static{
        //FIXME: id duplicated here and in initializer
        searchMap.put(1L, PHONE);
        searchMap.put(2L, EMAIL);
        searchMap.put(3L, WEB);
        searchMap.put(4L, SKYPE);
    }
    private String contactName;

    public String getContactName() {
        return contactName;
    }

    ContactType(long id, String name){
        this.contactName = name;
        //searchMap.put(id, this); compilation error!
    }

    public static ContactType getTypeById(long id){
        return searchMap.get(id);
    }
}
