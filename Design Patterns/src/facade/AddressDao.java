package facade;

public class AddressDao {
	public Address create(Address address){
		address.setAddressid(++Data.addressIndex);
		return Data.addressList.put(address.getAddressid(), address);
	}
	public Address remove(Address c){
		return null;
	}
	public Address update(Address c){
		return null;
	}
	public Address delete(Address c){
		return Data.addressList.remove(c.getCustid());
	}
}
