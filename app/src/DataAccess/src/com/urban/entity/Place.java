package com.urban.entity;

import java.util.Collection;

public interface Place {
	public  String getDescription();
	public  Double getAlt();
	public  Double getLat();
	public  Collection<Address> getAddresses();
}
