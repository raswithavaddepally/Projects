package simplefactory;


public class Item {

	String ItemNumber;
	 String Name;
	int ItemId;	
	 public int getItemId() {
	     return ItemId;
	}

	 public Item()
	 {
		
	 }
	 public Item(String itemNumber,String name)
	 {
		 this.ItemNumber = itemNumber;
		 this.Name = name;
		 
	 }
	 public String getName() {
	     return Name;
	}

	public void setName(String ItemName) {
	     this.Name = ItemName;
	}
	
	public String getItemNumber() {
	    return ItemNumber;
	}

	public void setItemNumber(String ItemNumber) {
	    this.ItemNumber = ItemNumber;
	}
	}
