package javarmi;

public class AddressDao {
	public Address create(Address address){
		address.setAddressid(++Data.addressIndex);
		return Data.addressList.put(address.getAddressid(), address);
	}
	public Address show(int addressId){
		return Data.addressList.get(addressId);
	}
	public Address update(Address c){
		return null;
	}
	public Address delete(Address c){
		return Data.addressList.remove(c.getCustid());
	}
}
