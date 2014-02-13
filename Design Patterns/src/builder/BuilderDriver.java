package builder;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BuilderDriver extends TestCase {

	/**
	 * Creates three DAO factories, and makes sure they all create the correct kind of DAO.
	 */
	public void testCollection(){
		Factory factory = new Factory();
		ChocalateCakeBuilder chocalateCakeBuilder = new ChocalateCakeBuilder();
		PineappleCakeBuilder pineappleCakeBuilder = new PineappleCakeBuilder();
		
		factory.setCakeBuilder(chocalateCakeBuilder);
		factory.constructCake();
		
		Cake cake1 = factory.getCake();
		assertEquals(cake1.getBatter(), "chocalateCakeBatter");
		assertEquals(cake1.getIcing(), "chocalateCakeIcing");
		assertEquals(cake1.getTransmission(), "chocalateCakeTransmission");
		
		factory.setCakeBuilder(pineappleCakeBuilder);
		factory.constructCake();
		
		Cake cake2 = factory.getCake();
		assertEquals(cake2.getBatter(), "pineappleCakeBatter");
		assertEquals(cake2.getIcing(), "pineappleCakeIcing");
		assertEquals(cake2.getTransmission(), "pineappleCakeTransmission");		
		
	}
	
	public static Test suite(){
		return new TestSuite(BuilderDriver.class);
	}
}
