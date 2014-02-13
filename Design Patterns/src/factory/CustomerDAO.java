package factory;



public class CustomerDAO {
 String name;
 int id;
 
 public CustomerDAO()
 {
	
 }
 public CustomerDAO(String name,int id)
 {
	 this.name = name;
	 this.id = id;
 }
 public String getName() {
     return name;
}

public void setID(int id) {
     this.id = id;
}
public int getID() {
    return id;
}

public void setName(String Fullname) {
    this.name = Fullname;
}

}
