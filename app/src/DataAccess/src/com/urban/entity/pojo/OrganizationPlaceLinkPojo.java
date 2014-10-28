package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Organization_Place")
public class OrganizationPlaceLinkPojo {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "organization")
    private OrganizationPojo organization;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "place", foreignAutoRefresh = true)
    private PlacePojo place;

	public OrganizationPojo getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationPojo organization) {
		this.organization = organization;
	}

	public PlacePojo getPlace() {
		return place;
	}

	public void setContact(PlacePojo place) {
		this.place = place;
	}

}
