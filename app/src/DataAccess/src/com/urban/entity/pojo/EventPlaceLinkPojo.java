package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Event_Place")
public class EventPlaceLinkPojo {
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "event")
	private EventPojo event;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "place", foreignAutoRefresh = true)
    private PlacePojo place;

	public EventPojo getEvent() {
		return event;
	}

	public void setEvent(EventPojo event) {
		this.event = event;
	}

	public PlacePojo getPlace() {
		return place;
	}

	public void setPlace(PlacePojo place) {
		this.place = place;
	}
	
}
