package com.urban.entity.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Contact;
import com.urban.entity.Event;
import com.urban.entity.Image;
import com.urban.entity.Organization;
import com.urban.entity.Place;

@DatabaseTable(tableName="Organizations")
public class OrganizationPojo implements Organization {
    public OrganizationPojo() {
    }

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(foreign = true, columnName = "logo", foreignAutoRefresh = true)
    private ImagePojo logo;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField
    private String description;
    
    @ForeignCollectionField(eager = true)
    private ForeignCollection<OrganizationContactLinkPojo> toContactLinks;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<OrganizationPlaceLinkPojo> toPlaces;
    
    @ForeignCollectionField(eager = true)
    private ForeignCollection<OrganizationEventLinkPojo> toEvents;

    private java.util.Set action = new java.util.HashSet();

    private void setId(int value) {
        this.id = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setLogo(int value) {
        setLogo(new Integer(value));
    }

    @Override
    public Image getLogo() {
        return logo;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String getDescription() {
        return description;
    }


    /* (non-Javadoc)
     * @see com.urban.entity.za.Organization#setAction(java.util.Set)
     */
    @Override
    public void setAction(java.util.Set value) {
        this.action = value;
    }

    /* (non-Javadoc)
     * @see com.urban.entity.za.Organization#getAction()
     */
    @Override
    public java.util.Set getAction() {
        return action;
    }

    public String toString() {
        return String.valueOf(getId());
    }

	@Override
	public Collection<Contact> getContacts() {
		Set<Contact> contacts = new HashSet<Contact>();
		
		for (OrganizationContactLinkPojo link : toContactLinks){
			contacts.add(link.getContact());
		};
		return contacts;
	}

	@Override
	public Collection<Place> getPlaces() {
		Set<Place> places = new HashSet<Place>();
		
		for (OrganizationPlaceLinkPojo link : toPlaces){
			places.add(link.getPlace());
		};
		return places;
	}
	
	
	@Override
	public Collection<Event> getEvents() {
		Set<Event> events = new HashSet<Event>();
		
		for (OrganizationEventLinkPojo link : toEvents){
			events.add(link.getEvent());
		};
		return events;
	}

}
