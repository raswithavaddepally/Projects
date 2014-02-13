package facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomerDao {
	public Customer create(Customer c){
		c.setCustid(++Data.custIndex);
		Data.customerList.put(c.getCustid(), c);
		return c;
	}
	public Customer update(Customer c){
		return null;
	}
	public void delete(int custId){
		Data.customerList.remove(custId);
		List<Address> addressList = this.getAddressess(custId);
		//Removing corresponding Addresses of the customer
		for (Iterator iterator = addressList.iterator(); iterator.hasNext();) {
			Address address = (Address) iterator.next();
			Data.addressList.remove(address.getAddressid());
		}
	}
	public List<Address> getAddressess(int custId){
		List<Address> addressList = new ArrayList<Address>();
		for (Iterator iterator = Data.addressList.keySet().iterator(); iterator.hasNext();) {
			Address address = Data.addressList.get((Integer)iterator.next());
			if(address.getCustid() == custId){
				addressList.add(address);
			}
			
		}
		return addressList;
	}
	public Customer getCustomer(int custId) {
		
		return Data.customerList.get(custId);
	}
}
