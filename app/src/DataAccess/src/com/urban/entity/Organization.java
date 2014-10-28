package com.urban.entity;

import java.util.Collection;

public interface Organization {

	public Long getId();

	public void setLogo(int value);

	public Image getLogo();

	public String getName();

	public String getDescription();
	
	public Collection<Contact> getContacts();
	
	public Collection<Place> getPlaces();
	
	public Collection<Event> getEvents();

	public void setAction(java.util.Set value);

	public java.util.Set getAction();

}