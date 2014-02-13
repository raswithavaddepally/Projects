package visitor;


import java.util.Vector;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 *
 */
public class VisitorMin implements Visitor{

	private int min = Integer.MIN_VALUE;
	private SuperMarketDepartment superMarketDepartment = null;
	
	/**
	 * 
	 */
	@Test
	
	public void testVisit(){
               Vector<SuperMarketDepartment> entertainmentDepartmentList = VisitorDriver.initializeTestList("DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		VisitorMin visitorMin = new VisitorMin();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMin);
		}

		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertaintmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
		
			Assert.assertTrue(entertaintmentDepartment.getQuantity()>=visitorMin.getMin());
		}
		System.out.println(visitorMin.getResult());
		System.out.println();
	}
	public void visit(EntertainmentDepartment entertainmentDepartment) {
		int quantity = entertainmentDepartment.getQuantity();
		if(quantity<min){
			min = quantity;
			superMarketDepartment = entertainmentDepartment;
		}
	}

	@Override
	public void visit(HealthNCareDepartment healthNCareDepartment) {
		int quantity = healthNCareDepartment.getQuantity();
		if(quantity<min){
			min = quantity;
			superMarketDepartment = healthNCareDepartment;
		}
	}
	
	
	
	
	@Override
	public String getResult(){
		if(superMarketDepartment!=null){
			String output = "";
			if(superMarketDepartment instanceof EntertainmentDepartment){
				EntertainmentDepartment entertaintmentDepartmant = (EntertainmentDepartment) superMarketDepartment;
				output = "EntertaintmentDepartmant " + entertaintmentDepartmant.getId() + " Manufacturer's Name " + entertaintmentDepartmant.getManufacturerName() + " and has the max quantity of " + entertaintmentDepartmant.getQuantity();
				return output;
			}
			else if(superMarketDepartment instanceof HealthNCareDepartment){
				HealthNCareDepartment healthNCareDepartment = (HealthNCareDepartment) superMarketDepartment;
				output = "HealthNCareDepartment " + healthNCareDepartment.getId() + " Product Type " + healthNCareDepartment.getType() + " and has the max quantity of " + healthNCareDepartment.getQuantity();
				return output;
			}
			
			return null;
		}
		else{
			return null;
		}
	}

	public int getMin(){
		return min;
	}
}
