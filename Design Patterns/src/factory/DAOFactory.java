package factory;

public abstract class DAOFactory {

	public abstract CustomerDAO GetCustomerDAO(int id);
	public abstract ItemDAO GetItemDAO(int id);
	public abstract void AddCustomer(CustomerDAO cust);
	public abstract void AddItem(ItemDAO item);
}

