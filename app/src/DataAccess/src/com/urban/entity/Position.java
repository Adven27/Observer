package com.urban.entity;

import java.util.Collection;


public interface Position {

    public Long getId();

    public void setName(String value);

    public String getName();

    public void addCategory(Category value);

    public void setOrganization(Organization value);

    public Organization getOrganization();
    
    public Collection<? extends Category> getCategories();

}