package com.urban.entity;

import java.io.InputStream;

public interface Image {	
	public Long getId();
    public byte [] getAsBinary();
    public InputStream getAsStream();
}
