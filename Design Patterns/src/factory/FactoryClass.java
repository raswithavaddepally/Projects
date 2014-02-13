package factory;

public class FactoryClass {
	public static DAOFactory getFactory()
	{
		String dbChoice = System.getProperty("dbVendor");
		
		if (dbChoice.equalsIgnoreCase("mysql"))
		{
			return new MysqlDAOFactory();
		}
		else if(dbChoice.equalsIgnoreCase("oracle"))
		{
			return new OracleDAOFactory();
		}
		else 
		{
			
			System.out.println("Database does not exist");
			 return null;
		}
	}

}

