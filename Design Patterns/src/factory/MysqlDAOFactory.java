package factory;

import java.util.HashMap;


public class MysqlDAOFactory extends DAOFactory {

	 static HashMap<Integer,String> map = new HashMap<Integer,String>();
	static HashMap<Integer,String> map1 = new HashMap<Integer,String>();
	 public void AddCustomer(CustomerDAO cust)
	{
	    String name = cust.getName();
	    int id = cust.getID();
	    map.put(id,name);
		System.out.println("Customer values inserted Successfully in MYSQL database with customer id: " + id);
	}
	
	public CustomerDAO GetCustomerDAO(int id)
	{
		
		String name = map.get(id);
		//System.out.println("Name of customer is: " + name );
		CustomerDAO cust = new CustomerDAO();
		cust.setID(id);
		cust.setName(name);
	    return cust;
	} 
	
	public ItemDAO GetItemDAO(int id)
	{
		String name = map1.get(id);
		//System.out.println("Name of customer is: " + name );
		ItemDAO item = new ItemDAO();
		item.setItemId(id);
		item.setName(name);
	    return item;
	}
		
	public void AddItem(ItemDAO item)
	{
		String name = item.getName();
	    int id = item.getItemId();
	    map1.put(id,name);
		System.out.println("Item values inserted Successfully in MYSQL database with ItemID: "+ id);
	}
}
