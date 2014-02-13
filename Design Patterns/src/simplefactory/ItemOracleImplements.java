package simplefactory;

import java.util.HashMap;


public class ItemOracleImplements extends ItemDAO{
	HashMap<Integer,String> mapItemMysql = new HashMap<Integer,String>();
	 public void insert(Item item)
	{
	    String name = item.getName();
	    int id = item.getItemId();

	    mapItemMysql.put(id,name);
		 System.out.println("Item values inserted Successfully in Oracle database");
	}
	
	public void retrieve(int id)
	{
		String name = mapItemMysql.get(id);
		System.out.println("Name of Item is: " + name );
	}
}
