package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Organization_Event")
public class OrganizationEventLinkPojo {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "organization")
    private OrganizationPojo organization;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "event", foreignAutoRefresh = true)
    private EventPojo event;

	public OrganizationPojo getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationPojo organization) {
		this.organization = organization;
	}

	public EventPojo getEvent() {
		return event;
	}

	public void setEvent(EventPojo event) {
		this.event = event;
	}

    
}
