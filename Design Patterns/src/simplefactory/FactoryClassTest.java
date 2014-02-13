package simplefactory;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class FactoryClassTest {
   
	private FactoryClass fc;

	@Before
	public void setUp() throws Exception {
		fc = new FactoryClass();
	}

	
	@Test
	public void testGetDAO() {
		Customer cust=new Customer();
		cust.setName("Sheldon sheldon");
		cust.setID(1);
		Customer cust1=new Customer();
		cust1.setName("Vaibhavi Sheth");
		cust1.setID(2);
		Item item = new Item();
		item.setItemNumber("103");
		item.setName("Banana");
		Item item1 = new Item();
		item1.setItemNumber("100");
		item1.setName("Chocolates");
		//get a reference to UserDAO object
		DAO custDAO= fc.getCustDAO();
		//call insert method by passing user object
		custDAO.insert(cust);
		custDAO.insert(cust1);
		custDAO.retrieve(2);
		
		DAO itemDAO = fc.getItemDAO();
		itemDAO.insert(item);
		itemDAO.insert(item1);
		itemDAO.retrieve(100);
	}

}
