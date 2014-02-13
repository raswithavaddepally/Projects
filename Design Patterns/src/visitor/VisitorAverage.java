package visitor;

import java.util.Vector;

import junit.framework.Assert;

import org.junit.Test;

public class VisitorAverage implements Visitor{

	private int total = 0;
	private int numStores = 0;
	
	/**
	 * Test a VisitorAverage against a list of EntertainmentDepartment.
	 */
	@Test
	public void testVisit(){
		Vector<SuperMarketDepartment> entertainmentDepartmentList = VisitorDriver.initializeTestList("DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		VisitorAverage visitorAverage = new VisitorAverage();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorAverage);
		}
		total = 0;
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertainmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
			total += entertainmentDepartment.getQuantity();
		}
		//Assert.assertTrue(visitorAverage.getAverage()==(total/entertainmentDepartmentList.size()));
		System.out.println(visitorAverage.getResult());
		System.out.println();
	}
	
	@Override
	public void visit(EntertainmentDepartment entertainmentDepartment) {
		total += entertainmentDepartment.getQuantity();
		numStores++;
	}

	
	@Override
	public void visit(HealthNCareDepartment healthNCareDepartment) {
		total += healthNCareDepartment.getQuantity();
		numStores++;
	}

	@Override
	public String getResult() {
		if(numStores>0){
			return "The stores have an average quantity of " + total / numStores;
		}
		return null;
	}

	public int getAverage() {
		return total / numStores;
	}

}