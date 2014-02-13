package simplefactory;

public class Customer {
 String name;
 int id;
 
 public Customer()
 {
	
 }
 public Customer(String name,int id)
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
