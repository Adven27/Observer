package com.urban.entity.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Address;
import com.urban.entity.Place;

@DatabaseTable(tableName="Places")
public class PlacePojo implements Place {
	
    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField(canBeNull = false)
    private Double lat;
    
    @DatabaseField(canBeNull = false)
    private Double alt;
    
    @DatabaseField(canBeNull = true)
    private String description;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<PlaceAddressLinkPojo> toAddressLinks;

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Double getAlt() {
		return alt;
	}

	@Override
	public Double getLat() {
		return lat;
	}
	
	@Override
	public Collection<Address> getAddresses() {
		Set<Address> places = new HashSet<Address>();
		
		for (PlaceAddressLinkPojo link : toAddressLinks){
			places.add(link.getAddress());
		};
		return places;
	}

}
