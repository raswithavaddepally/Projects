package facade;

import static org.junit.Assert.*;


import org.junit.Test;

public class FacadeTest {
	
	
	@Test
	public void insertCustomerTest(){
		Customer customer =new Customer();
		customer.setName("Raswitha");
		customer.setSex("F");
		customer.setAge(21);
		Address[] address = new Address[1];
		address[0] = new Address();
		address[0].setHouseNo("433");
		address[0].setStreetName("WaterView Parkway");
		address[0].setZipcode(75080);
		FacadeInterface facade = new CustomerManagementService();
		int custId = facade.createCustomerInfo(customer, address);
		assertEquals("Raswitha",Data.customerList.get(custId).getName());
		Address addressOutput = new CustomerDao().getAddressess(custId).get(0);
		assertEquals(addressOutput,address[0]);
		
	}
	@Test
	public void deleteCustomerTest(){
		Customer customer =new Customer();
		customer.setName("Raswitha");
		customer.setSex("F");
		customer.setAge(21);
		Address[] address = new Address[1];
		address[0] = new Address();
		address[0].setHouseNo("433");
		address[0].setStreetName("WaterView Parkway");
		address[0].setZipcode(75080);
		FacadeInterface facade = new CustomerManagementService();
		int custId = facade.createCustomerInfo(customer, address);
		facade.deleteCustomerInfo(custId);
		assertEquals(null ,  new CustomerDao().getCustomer(custId));
	}
}
