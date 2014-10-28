package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Address;

@DatabaseTable(tableName="Addresses")
public class AddressPojo implements Address {
    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField(canBeNull = false)
    private String street;
    
    @DatabaseField(canBeNull = false)
    private Integer house;

    @DatabaseField(canBeNull = true)
    private String letter;
    
    @DatabaseField(canBeNull = true)
    private Integer floor;
    
    @DatabaseField(canBeNull = true)
    private Integer office;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouse() {
		return house;
	}

	public void setHouse(Integer house) {
		this.house = house;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getOffice() {
		return office;
	}

	public void setOffice(Integer office) {
		this.office = office;
	}
    
    
}
