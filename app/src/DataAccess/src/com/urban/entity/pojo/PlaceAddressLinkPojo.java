package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Place_Address")
public class PlaceAddressLinkPojo {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "place")
    private PlacePojo place;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "address", foreignAutoRefresh = true)
    private AddressPojo address;

	public PlacePojo getPlace() {
		return place;
	}

	public void setPlace(PlacePojo place) {
		this.place = place;
	}

	public AddressPojo getAddress() {
		return address;
	}

	public void setAddress(AddressPojo address) {
		this.address = address;
	}
}
