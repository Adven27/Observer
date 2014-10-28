package com.urban.entity.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.urban.entity.Advertising;
import com.urban.entity.Image;
import com.urban.entity.Position;

@DatabaseTable(tableName="Advertisings")
public class AdvertisingPojo implements Advertising {
    public AdvertisingPojo() {
    }

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField(foreign = true, foreignAutoRefresh=true, maxForeignAutoRefreshLevel= 2, canBeNull = true, columnName = "organization")
    private PositionPojo position;

    @DatabaseField
    private int priority;

    @DatabaseField
    private String text;

    @DatabaseField(foreign = true, foreignAutoRefresh=true, maxForeignAutoRefreshLevel= 2, columnName = "image")
    private ImagePojo image;

    public Image getImage() {
		return image;
	}

	@Override
    public Long getId() {
        return id;
    }

    @Override
    public void setPriority(int value) {
        this.priority = value;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void setText(String value) {
        this.text = value;
    }

    @Override
    public String getText() {
        return text;
    }

    public String toString() {
        return String.valueOf(getId());
    }

	@Override
	public void setPosition(Position value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
