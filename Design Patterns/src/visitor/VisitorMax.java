package visitor;


import java.util.Vector;


import junit.framework.Assert;

import org.junit.Test;
import org.junit.*;

public class VisitorMax implements Visitor {

	private int max = 0;
	private SuperMarketDepartment superMarketDepartment = null;
	
	/**
	 * Test a VisitorMax against a list of EntertaintmentDepartment.
	 */
	@Test
	public void testVisit(){
               Vector<SuperMarketDepartment> entertainmentDepartmentList = VisitorDriver.initializeTestList("DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		VisitorMax visitorMax = new VisitorMax();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMax);
		}

		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertainmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
		
			Assert.assertTrue(entertainmentDepartment.getQuantity()<=visitorMax.getMax());
		}
		System.out.println(visitorMax.getResult());
		System.out.println();
	}
	
	@Override
	public void visit(EntertainmentDepartment entertainmentDepartment) {
		int quantity = entertainmentDepartment.getQuantity();
		if(quantity>max){
			max = quantity;
			superMarketDepartment = entertainmentDepartment;
		}
	}

	@Override
	public void visit(HealthNCareDepartment healthNCareDepartment) {
		int quantity = healthNCareDepartment.getQuantity();
		if(quantity>max){
			max = quantity;
			superMarketDepartment = healthNCareDepartment;
		}
	}
	
	
	

	@Override
	public String getResult(){
		if(superMarketDepartment!=null){
			String output = "";
			if(superMarketDepartment instanceof EntertainmentDepartment){
				EntertainmentDepartment entertainmentDepartmant = (EntertainmentDepartment) superMarketDepartment;
				output = "EntertainmentDepartmant " + entertainmentDepartmant.getId() + " Manufacturer's Name " + entertainmentDepartmant.getManufacturerName() + " and has the max quantity of " + entertainmentDepartmant.getQuantity();
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
	
	public int getMax(){
		return max;
	}
}