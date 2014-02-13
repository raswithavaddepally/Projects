package singleton;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class SingletonTest {
    
	@Before
	public void setUp() throws Exception {
		
	}


	@Test
	public void test() {
		 Singleton service = Singleton.getInstance();
		 System.out.println("First fibonacii series");
	        service.fibonacci(10);
	        System.out.println("\n");
	        System.out.println("Second fibonacii series");
	        service.fibonacci(20);
	}

}
