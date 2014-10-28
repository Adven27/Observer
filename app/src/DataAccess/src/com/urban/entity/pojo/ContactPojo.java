package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Contact;
import com.urban.entity.ContactType;

@DatabaseTable(tableName="Contacts")
public class ContactPojo implements Contact{

    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField(canBeNull = false)
    private String info;
    
    @DatabaseField(canBeNull = false)
    private Long type;
    
	@Override
	public ContactType getType() {
		return ContactType.getTypeById(type);
	}

	@Override
	public String getInfo() {
		return info;
	}

}
