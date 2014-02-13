package simplefactory;


public class FactoryClass {
   
	public DAO getCustDAO ()
	{
		String dbChoice = System.getProperty("dbVendor");
		if (dbChoice.equalsIgnoreCase("mysql"))
		{
			return new CustomerMYSQLImplements();
		}
		else if(dbChoice.equalsIgnoreCase("oracle"))
		{
			return new CustomerOracleImplements();
		}
		else
		{
			System.out.println("Database does not exist");
			return null;
		}
	}
	public DAO getItemDAO()
	{
		String dbChoice = System.getProperty("dbVendor");
		if (dbChoice.equalsIgnoreCase("mysql"))
		{
			return new ItemMYSQLImplements();
		}
		else if(dbChoice.equalsIgnoreCase("oracle"))
		{
			return new ItemOracleImplements();
		}
		else
		{
			System.out.println("Database does not exist");
			return null;
		}
	}
}
