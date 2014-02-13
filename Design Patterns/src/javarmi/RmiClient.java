package javarmi;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;


 
public class RmiClient { 
    // "obj" is the reference of the remote object
    FacadeRemoteInterface obj = null; 
 
    public FacadeRemoteInterface getCustomerService() { 
        try { 
            obj = (FacadeRemoteInterface) Naming.lookup("//localhost:1099/RmiServer");
            System.out.println("Hello World");
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
           int custId = obj.createCustomerInfo(customer, address);
           System.out.println("On client " + custId);
           obj.showCustomerInfo(custId);
          return  obj;
        } catch (Exception e) { 
            System.err.println("RmiClient exception: " + e); 
            e.printStackTrace(); 
 
        } 
        return null;
    } 
 
    public static void main(String args[]) throws RemoteException {
        // Create and install a security manager
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
 
        RmiClient cli = new RmiClient();
        cli.getCustomerService();
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
		/*int custId = facade.createCustomerInfo(customer, address);
		System.out.println(custId);*/
    }
}