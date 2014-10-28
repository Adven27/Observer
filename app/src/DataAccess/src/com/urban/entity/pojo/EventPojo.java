package com.urban.entity.pojo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Event;
import com.urban.entity.Place;

@DatabaseTable(tableName="Events")
public class EventPojo implements Event{
    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField(canBeNull = true)
    private String text;
    
    @DatabaseField(foreign = true, foreignAutoRefresh=true, maxForeignAutoRefreshLevel= 2, columnName = "image")
    private ImagePojo image;
    
    @ForeignCollectionField(eager = true)
    private ForeignCollection<EventPlaceLinkPojo> toPlaces;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ImagePojo getImage() {
		return image;
	}

	public void setImage(ImagePojo image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<Place> getPlaces() {
		Set<Place> places = new HashSet<Place>();
		
		for (EventPlaceLinkPojo link : toPlaces){
			places.add(link.getPlace());
		};
		return places;
	}

}
