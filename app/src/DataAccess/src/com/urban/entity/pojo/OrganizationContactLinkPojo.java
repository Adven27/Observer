package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Organization_Contact")
public class OrganizationContactLinkPojo {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "organization")
    private OrganizationPojo organization;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "contact", foreignAutoRefresh = true)
    private ContactPojo contact;

	public OrganizationPojo getOrganization() {
		return organization;
	}

	public ContactPojo getContact() {
		return contact;
	}
}
