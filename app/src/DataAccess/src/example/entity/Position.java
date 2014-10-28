package example.entity;

import com.j256.ormlite.field.DatabaseField;

public class Position {
	
    @DatabaseField(generatedId = true)
    private int id;
    
	@DatabaseField(index = true)
	private String name;
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@DatabaseField(canBeNull = false, foreign = true)
	private Category category;
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
