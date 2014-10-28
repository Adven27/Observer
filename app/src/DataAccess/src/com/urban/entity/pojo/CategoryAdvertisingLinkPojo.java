package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Advertising;
import com.urban.entity.Category;

@DatabaseTable(tableName="Category_Advertising")
public class CategoryAdvertisingLinkPojo {

	@DatabaseField(canBeNull = false, foreign = true, columnName = "advertising", foreignAutoRefresh = true,  maxForeignAutoRefreshLevel = 1 )
    private AdvertisingPojo advertising;
	
	@DatabaseField(canBeNull = false, foreign = true, columnName = "category")
    private CategoryPojo category;
	
	public Advertising getAdvertising(){
		return advertising;
	}

	public Category getCategory(){
		return category;
	}
}
