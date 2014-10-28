package com.urban.entity.pojo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Image;

@DatabaseTable(tableName="Images")
public class ImagePojo implements Image{
    @DatabaseField(generatedId = true)
    private Long id;
    
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] binaryContent;

	@Override
	public byte[] getAsBinary() {
		return binaryContent;
	}

	@Override
	public InputStream getAsStream() {
		return new ByteArrayInputStream(binaryContent);
	}

	@Override
	public Long getId() {
		return id;
	}
}
