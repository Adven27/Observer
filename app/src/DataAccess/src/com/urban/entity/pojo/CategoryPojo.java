package com.urban.entity.pojo;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Advertising;
import com.urban.entity.Category;
import com.urban.entity.Position;

@DatabaseTable(tableName="Categories")
public class CategoryPojo implements Category {
    public CategoryPojo() {
    }

    @DatabaseField(generatedId = true)
    private int id;

	@DatabaseField(canBeNull = false)
    private String name;

	@DatabaseField(foreign = true, columnName = "parent")
    private CategoryPojo parent;

	@DatabaseField(canBeNull = false)
    private int order;
	
    @ForeignCollectionField(eager = true)
    private ForeignCollection<CategoryPositionLinkPojo> toPositionsLinks;
    
    @ForeignCollectionField(eager = true)
    private ForeignCollection<CategoryAdvertisingLinkPojo> toAdvertLinks;
    
	ForeignCollection<CategoryPositionLinkPojo> getPositionLinks() {
		return toPositionsLinks;
	}

    private java.util.Set<Position> position = new java.util.HashSet<Position>();

    @Override
    public long getId() {
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
    public void setParent(Category value) {
        this.parent = (CategoryPojo)value;
    }

    /* (non-Javadoc)
     * @see com.urban.entity.za.Category#getParent()
     */
    @Override
    public CategoryPojo getParent() {
        return parent;
    }

    /* (non-Javadoc)
     * @see com.urban.entity.za.Category#setOrder(int)
     */
    @Override
    public void setOrder(int value) {
        this.order = value;
    }

    /* (non-Javadoc)
     * @see com.urban.entity.za.Category#getOrder()
     */
    @Override
    public int getOrder() {
        return order;
    }

    /* (non-Javadoc)
     * @see com.urban.entity.za.Category#setPosition(java.util.Set)
     */
    @Override
    public void addPosition(Position value) {
        position.add(value);
    }

    @Override
    public Collection<Position> getPositions() {
        SortedSet<Position> posSet = new TreeSet<Position>(new Comparator<Position>(){
        	public int compare(Position pos1, Position pos2){
        		if (pos1 == pos2)
        			return 0;
        		if (pos1 == null)
        			return 1;
        		if (pos2 == null)
        			return -1;
        		return pos1.getId().compareTo(pos2.getId());
        	};
        });
        
        for (CategoryPositionLinkPojo posLink: toPositionsLinks){
        	posSet.add(posLink.getPosition());
        }
    	return posSet;
    }
    
    public Collection<Advertising> getAdvertisings() {
        /*SortedSet<Advertising> posSet = new TreeSet<Advertising>(new Comparator<Advertising>(){
        	public int compare(Advertising pos1, Advertising pos2){
        		if (pos1 == pos2)
        			return 0;
        		if (pos1 == null)
        			return 1;
        		if (pos2 == null)
        			return -1;
        		return pos1.getId().compareTo(pos2.getId());
        	};
        });*/
    	//TODO: think about special order here. Do we need it?
    	Set<Advertising> elems = new HashSet<Advertising>();
        for (CategoryAdvertisingLinkPojo link: toAdvertLinks){
        	elems.add(link.getAdvertising());
        }
    	return elems;
    }


    public String toString() {
        return String.valueOf(getId());
    }

	@Override
	public Collection<Category> getChildren() {
		return null;
	}

}
