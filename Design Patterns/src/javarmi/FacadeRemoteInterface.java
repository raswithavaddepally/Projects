package javarmi;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;



public interface FacadeRemoteInterface extends Remote,Serializable {

	public void getMessage() throws RemoteException;
	
	public int createCustomerInfo(Customer customer, Address[] address) throws RemoteException;

	public void showCustomerInfo(int custId) throws RemoteException;

	public void deleteCustomerInfo(int custId) throws RemoteException;

	public void updateCustomerInfo(int custId, Address address) throws RemoteException;
}
