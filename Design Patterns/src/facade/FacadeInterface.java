package facade;

import java.awt.List;

public abstract class FacadeInterface {

	public abstract int createCustomerInfo(Customer customer, Address[] address);

	public abstract void showCustomerInfo(int custId);

	public abstract void deleteCustomerInfo(int custId);

	public abstract  void updateCustomerInfo(int custId, Address address);
	
	public static  FacadeInterface createCustomerManagementService(){
		return new CustomerManagementService();
	}

}
