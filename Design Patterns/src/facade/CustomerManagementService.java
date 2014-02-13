package facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerManagementService extends FacadeInterface {
	List<Address> getAddress(int custId){
		Customer c = Data.customerList.get(custId);
		List<Address> address =  new ArrayList<Address>();
		
		return address;
	}
	
	 public synchronized int createCustomerInfo(Customer customer ,Address[] address){
		CustomerDao customerDao = new CustomerDao();
		customer = customerDao.create(customer);
		for (int i = 0; i < address.length; i++) {
			AddressDao addressDao = new AddressDao();
			address[i].setCustid(customer.getCustid());
			Address a1 = addressDao.create(address[i]);
		}
		return customer.getCustid();
	}
	public synchronized void showCustomerInfo(int custId){
		System.out.println();
		System.out.println();
		System.out.println("Getting Customer Information " + custId);
		System.out.println("------------------------------------------------");
		CustomerDao customerDao = new CustomerDao();
		Customer customer = customerDao.getCustomer(custId);
		if( customer == null ){
			System.out.println("No customer Exists with id " + custId);
			return;
		}
		List<Address> addressList = customerDao.getAddressess(custId);
		System.out.println("==========================================");
		System.out.println("CUSTOMER NAME ::  " + customer.getName());
		System.out.println("CUSTOMER AGE  ::  " + customer.getAge());
		System.out.println("CUSTOMER SEX  ::  " + customer.getSex());
		System.out.println("ADDRESSESS    ::");
		for (int i = 0; i < addressList.size(); i++) {
			System.out.println("  Address " + (i+1));
			System.out.println("------------------");
			System.out.println(" Street Name " + addressList.get(i).getStreetName());
			System.out.println(" House Number " + addressList.get(i).getHouseNo());
			System.out.println(" Zip Code " + addressList.get(i).getZipcode());
		}
		System.out.println("=============================================");
	}
	public synchronized void deleteCustomerInfo(int custId){
		System.out.println();
		System.out.println();
		System.out.println("Deleting Customer with id " + custId );
		System.out.println("------------------------------------------------------");
		CustomerDao customerDao = new CustomerDao();
		customerDao.delete(custId);
	}
	public synchronized void updateCustomerInfo(int custId,Address address){
		System.out.println();
		System.out.println();
		System.out.println("Updating Customer with id " + custId );
		System.out.println("------------------------------------------------------");
		address.setCustid(custId);
		AddressDao addressDao = new AddressDao();
		addressDao.create(address);
	}
	
}
