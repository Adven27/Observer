package com.urban.entity;

import java.util.Collection;

public interface Category {

	public long getId();

	public void setName(String value);

	public String getName();

	public void setParent(Category value);

	public Category getParent();

	public void setOrder(int value);

	public int getOrder();

	public void addPosition(Position value);
	
	public Collection<Category> getChildren();

	public Collection<Position> getPositions();
	
	public Collection<Advertising> getAdvertisings();

}