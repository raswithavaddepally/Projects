package javarmi;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

public class RmiProxyTest {

	
	public void insertCustomerTest() throws RemoteException{
		Customer customer =new Customer();
		customer.setName("Rosy");
		customer.setSex("F");
		customer.setAge(22);
		Address[] address = new Address[1];
		address[0] = new Address();
		address[0].setHouseNo("4810");
		address[0].setStreetName("Allen Springlands");
		address[0].setZipcode(75080);
		FacadeRemoteInterface facade = new RmiClient().getCustomerService();
		
	}

	public void deleteCustomerTest() throws RemoteException{
		Customer customer =new Customer();
		customer.setName("Rosy");
		customer.setSex("F");
		customer.setAge(22);
		Address[] address = new Address[1];
		address[0] = new Address();
		address[0].setHouseNo("4810");
		address[0].setStreetName("Allen Springlands");
		address[0].setZipcode(75080);
		FacadeRemoteInterface facade = new RmiClient().getCustomerService();
		
	}
}
