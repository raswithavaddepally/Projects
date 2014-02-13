package simplefactory;

import java.util.HashMap;


public class CustomerOracleImplements extends CustomerDAO{

	 HashMap<Integer,String> map = new HashMap<Integer,String>();
	 public void insert(Customer cust)
	{
	    String name = cust.getName();
	    int id = cust.getID();
				
		 map.put(id,name);
		 System.out.println("Customer table values inserted Successfully in oracle database");
	}
	
	public void retrieve(int id)
	{
		
		String name = map.get(id);
		System.out.println("Name of customer is: " + name +"with id: " + id);
	} 
	
	
}
