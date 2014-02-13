package visitor;
public class EntertainmentDepartment implements SuperMarketDepartment{

	private int id;
	private String manufacturerName;
	private int quantity;
	
	public EntertainmentDepartment(int i, String mn, int q) {
		id = i;
		manufacturerName = mn;
		quantity = q;
	}

	public int getId(){
		return id;
	}
	
	public String getManufacturerName(){
		return manufacturerName;
	}
	
	public int getQuantity(){
		return quantity;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

