package visitor;

public interface Visitor {
	
	
	public String getResult();

	public void visit(HealthNCareDepartment healthNCareDepartment);
	
	public void visit(EntertainmentDepartment entertainmentDepartment);
	
}