package factory;
public class ItemDAO {

    String Name;
	int ItemId;	
	 public int getItemId() {
	     return ItemId;
	}

	 public ItemDAO()
	 {
		
	 }
	 public ItemDAO(int id,String name)
	 {
		 this.ItemId = id ;
		 this.Name = name;
		 
	 }
	 public String getName() {
	     return Name;
	}

	public void setName(String ItemName) {
	     this.Name = ItemName;
	}
	
	public void setItemId(int Id) {
	    this.ItemId = Id;
	}
	}
