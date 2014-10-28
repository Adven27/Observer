package com.urban.entity.pojo;

import java.util.Collection;

import com.example.prototype.dao.DAO;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Category;
import com.urban.entity.Organization;
import com.urban.entity.Position;

@DatabaseTable(tableName="Positions")
public class PositionPojo implements Position {
    public PositionPojo() {
    }

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField(foreign = true, columnName = "organization")
    private OrganizationPojo organization;

    private java.util.Set categories = new java.util.HashSet();

    private java.util.Set page = new java.util.HashSet();

    private void setId(long value) {
        this.id = value;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setName(String value) {
        this.name = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addCategory(Category value) {
        this.categories.add(value);
    }

    @Override
    public void setOrganization(Organization value) {
    	//FIXME: move this cast
        this.organization = (OrganizationPojo)value;
    }

    @Override
    public Organization getOrganization() {
    	
        return organization != null ? DAO.get(Organization.class, organization.getId()) : null;
    }

    public void setPage(java.util.Set value) {
        this.page = value;
    }

    public java.util.Set getPage() {
        return page;
    }


    public String toString() {
        return String.valueOf(getId());
    }

	@Override
	public Collection<? extends Category> getCategories() {
		// TODO implement
		throw new UnsupportedOperationException();
	}

}
