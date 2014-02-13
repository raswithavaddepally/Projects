package javarmi;



import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*; 
import java.util.List;

 
public class RmiServer extends UnicastRemoteObject implements FacadeRemoteInterface {
    public static final String MESSAGE = "Hello world";
 
    public RmiServer() throws RemoteException {
    }
 

	    public static void main(String args[]) throws RemoteException {
    	RmiServer server = new RmiServer();
    	server.startServer();
    }
    public void startServer(){
    	 System.out.println("RMI server started");
    	 
         // Create and install a security manager
         if (System.getSecurityManager() == null) {
             System.setSecurityManager(new RMISecurityManager());
             System.out.println("Security manager installed.");
         } else {
             System.out.println("Security manager already exists.");
         }
  
         try { //special exception handler for registry creation
             LocateRegistry.createRegistry(1099); 
             System.out.println("java RMI registry created.");
         } catch (RemoteException e) {
             //do nothing, error means registry already exists
             System.out.println("java RMI registry already exists.");
         }
  
         try {
             //Instantiate RmiServer
             RmiServer obj = new RmiServer();
  
             // Bind this object instance to the name "RmiServer"
             Naming.rebind("//localhost/RmiServer", obj);
  
             System.out.println("PeerServer bound in registry");
         } catch (Exception e) {
             System.err.println("RMI server exception:" + e);
             e.printStackTrace();
         }

    }


	@Override
	public void getMessage() throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int createCustomerInfo(Customer customer, Address[] address)
			throws RemoteException {
		CustomerDao customerDao = new CustomerDao();
		customer = customerDao.create(customer);
		for (int i = 0; i < address.length; i++) {
			AddressDao addressDao = new AddressDao();
			address[i].setCustid(customer.getCustid());
			Address a1 = addressDao.create(address[i]);
		}
		return customer.getCustid();
	}


	@Override
	public void showCustomerInfo(int custId) throws RemoteException {
		// TODO Auto-generated method stub
				System.out.println();
				System.out.println();
				System.out.println(">>>>>>>>> Getting Customer Information " + custId);
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
		
	}


	@Override
	public void deleteCustomerInfo(int custId) throws RemoteException {
		// TODO Auto-generated method stub
				System.out.println();
				System.out.println();
				System.out.println(">>>>>>>>>>>>>>>>> Deleting Customer with id " + custId );
				System.out.println("------------------------------------------------------");
				CustomerDao customerDao = new CustomerDao();
				customerDao.delete(custId);
	}


	@Override
	public void updateCustomerInfo(int custId, Address address)
			throws RemoteException {
		System.out.println();
		System.out.println();
		System.out.println(">>>>>>>>>>>>>>>>> Updating Customer with id " + custId );
		System.out.println("------------------------------------------------------");
		address.setCustid(custId);
		AddressDao addressDao = new AddressDao();
		addressDao.create(address);
		
	}
	
}

