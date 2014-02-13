package visitor;

public class HealthNCareDepartment implements SuperMarketDepartment{

	private int id;
	private String type;
	private int quantity;
	
	public HealthNCareDepartment(int i, String t, int q) {
		id = i;
		type = t;
		quantity = q;
	}

	public int getId(){
		return id;
	}
	
	public String getType(){
		return type;
	}
	
	public int getQuantity(){
		return quantity;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}
