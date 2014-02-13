package facade;

public class Client  {
	public static void main(String[] args) {
		Customer customer = new Customer();
		customer.setName("Raswitha");
		customer.setSex("F");
		customer.setAge(22);
		Address[] address = new Address[2];
		address[0] = new Address();
		address[0].setHouseNo("433");
		address[0].setStreetName("WaterView Parkway");
		address[0].setZipcode(75080);
		address[1] = new Address();
		address[1].setHouseNo("30205");
		address[1].setStreetName("University Village");
		address[1].setZipcode(75000);
		FacadeInterface facade = FacadeInterface.createCustomerManagementService();
		int custId = facade.createCustomerInfo(customer, address);
		
		////
		
		facade.showCustomerInfo(1);
		
		Address address1 = new Address();
		address1.setHouseNo("1234");
		address1.setStreetName("Chatham Courts");
		address1.setZipcode(12345);
		facade.updateCustomerInfo(custId, address1);
		
		
		facade.showCustomerInfo(1);
		
		facade.deleteCustomerInfo(1);
		facade.showCustomerInfo(1);
	}
}
