
package factory;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AbstractFactoryPatternTest {

	
	private FactoryClass fc;
   
	@Before
	public void setUp() throws Exception {
		fc = new FactoryClass();
		
	}

	@Test
	public void test() {
		CustomerDAO cust=new CustomerDAO();
		cust.setName("Sheldon sheldon");
		cust.setID(1);
		CustomerDAO cust1=new CustomerDAO();
		cust1.setName("Vaibhavi Sheth");
		cust1.setID(2);
		
		// creating object of Item class
		ItemDAO item = new ItemDAO();
		item.setItemId(1);
		item.setName("Chocolates");
		
		ItemDAO item1 = new ItemDAO();
		item1.setItemId(2);
		item1.setName("Apple");
		
		
		//get a reference to CustomerDAO object
		 DAOFactory custDAO= fc.getFactory();
			
		 DAOFactory itemDAO = fc.getFactory();
			//call insert method by passing user object
			custDAO.AddCustomer(cust);
			itemDAO.AddItem(item);
			custDAO.AddCustomer(cust1);
			itemDAO.AddItem(item1);  
			CustomerDAO c = custDAO.GetCustomerDAO(1);
			ItemDAO i = itemDAO.GetItemDAO(2);
			
			System.out.println("Id of Customer is: " + c.getID() + " and name: " + c.getName());
			System.out.println("Id of Item is: " + i.getItemId() + " and name: " + i.getName());
		}
		
}
