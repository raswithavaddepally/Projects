package javarmi;

import java.io.Serializable;
import java.util.HashMap;

public class Data implements Serializable {
	
	private static final long serialVersionUID = 4917193179077273368L;
	public static int custIndex;
	public static int addressIndex;
	public static HashMap<Integer,Customer> customerList = new HashMap<Integer,Customer>();
	public static HashMap<Integer,Address> addressList = new HashMap<Integer,Address>();
	
}
