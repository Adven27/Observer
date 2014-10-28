package example.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Category {
    @DatabaseField(generatedId = true)
    private int id;
    
	@DatabaseField(index = true)
	private String name;
	public String getName() {
		return name;
	}
	
    @ForeignCollectionField(eager = false)
    private ForeignCollection<Position> orders;
    
	public ForeignCollection<Position> getOrders() {
		return orders;
	}

	public void setOrders(ForeignCollection<Position> orders) {
		this.orders = orders;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	/*public void addPosition(Position pos){
		orders = orders == null ? new ForeignCollection<Position>() : orders;
	}*/
}
