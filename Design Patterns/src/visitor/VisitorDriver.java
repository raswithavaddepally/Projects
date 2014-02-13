package visitor;

import java.util.Random;

import java.util.Vector;

import org.junit.Test;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

public class VisitorDriver{

	private static Random rand = null;

	@Test
	public void testGetMax(){
		VisitorMax visitorMax = null;

		Vector<SuperMarketDepartment> entertainmentDepartmentList = initializeTestList("DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		visitorMax = new VisitorMax();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMax);
		}
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertainmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
			Assert.assertTrue(entertainmentDepartment.getQuantity()<=visitorMax.getMax());
		}
		System.out.println(visitorMax.getResult());
		
		Vector<SuperMarketDepartment> healthNCareDepartmentList = initializeTestList("Product", new String[]{"Conditioner", "Shampoo", "Eyeliner"});
		visitorMax = new VisitorMax();
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = healthNCareDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMax);
		}
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			HealthNCareDepartment healthNCareDepartment = (HealthNCareDepartment) healthNCareDepartmentList.elementAt(ctr);
			Assert.assertTrue(healthNCareDepartment.getQuantity()<=visitorMax.getMax());
		}
		System.out.println(visitorMax.getResult());
		
		
		System.out.println();
	}
	
	

	@Test
public void testGetMin(){
		VisitorMin visitorMin = null;

		Vector<SuperMarketDepartment> entertainmentDepartmentList = initializeTestList("CDS/DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		visitorMin = new VisitorMin();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMin);
		}
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertainmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
			Assert.assertTrue(entertainmentDepartment.getQuantity()<=visitorMin.getMin());
		}
		System.out.println(visitorMin.getResult());
		
		Vector<SuperMarketDepartment> healthNCareDepartmentList = initializeTestList("Product", new String[]{"Conditioner", "Shampoo", "Eyeliner"});
		visitorMin = new VisitorMin();
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = healthNCareDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorMin);
		}
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			HealthNCareDepartment healthNCareDepartment = (HealthNCareDepartment) healthNCareDepartmentList.elementAt(ctr);
			Assert.assertTrue(healthNCareDepartment.getQuantity()<=visitorMin.getMin());
		}
		System.out.println(visitorMin.getResult());
		
		
		System.out.println();
	}
	
	
	
	@Test

public void testGetAverage(){
		VisitorAverage visitorAverage = null;

		Vector<SuperMarketDepartment> entertainmentDepartmentList =initializeTestList("CDS/DVds", new String[]{"Dark Night Rises", "Brave", "Up", "Men In Black 3", "Unknown"});
		visitorAverage = new VisitorAverage();
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = entertainmentDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorAverage);
		}
		for(int ctr=0; ctr<entertainmentDepartmentList.size(); ctr++){
			EntertainmentDepartment entertainmentDepartment = (EntertainmentDepartment) entertainmentDepartmentList.elementAt(ctr);
			Assert.assertTrue(entertainmentDepartment.getQuantity()<=visitorAverage.getAverage());
		}
		System.out.println(visitorAverage.getResult());
		
		Vector<SuperMarketDepartment> healthNCareDepartmentList = initializeTestList("Product", new String[]{"Conditioner", "Shampoo", "Eyeliner"});
		visitorAverage = new VisitorAverage();
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			 SuperMarketDepartment superMarketDepartment = healthNCareDepartmentList.elementAt(ctr);
			 superMarketDepartment.accept(visitorAverage);
		}
		for(int ctr=0; ctr<healthNCareDepartmentList.size(); ctr++){
			HealthNCareDepartment healthNCareDepartment = (HealthNCareDepartment) healthNCareDepartmentList.elementAt(ctr);
			Assert.assertTrue(healthNCareDepartment.getQuantity()<=visitorAverage.getAverage());
		}
		System.out.println(visitorAverage.getResult());
		
		
		System.out.println();
	}
	
	public static Vector<SuperMarketDepartment> initializeTestList(String type, String[] params){
		initializeRand();
		Vector<SuperMarketDepartment> list = new Vector<SuperMarketDepartment>();
		for(int ctr=0; ctr<params.length; ctr++){
			if(type.equals("Entertainment")){
				list.add(new EntertainmentDepartment(ctr, params[ctr], rand.nextInt(1000)));
			}
			else if(type.equals("HealthNCareDepartment")){
				list.add(new HealthNCareDepartment(ctr, params[ctr], rand.nextInt(1000)));
			}
			
			}
		
		return list;
}
	private static void initializeRand() {
		long seed = System.currentTimeMillis();
		if(rand==null){
			rand = new Random(seed);
		}
	}

	public static junit.framework.Test suite(){
	    return new JUnit4TestAdapter(VisitorDriver.class); 
	}



	
	}


