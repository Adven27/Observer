package com.urban.entity;

public interface Advertising {

	public Long getId();

	public void setPosition(Position value);

	public Position getPosition();

	public void setPriority(int value);

	public int getPriority();

	public void setText(String value);

	public String getText();
	
	public Image getImage();
}