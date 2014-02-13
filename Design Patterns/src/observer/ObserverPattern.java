package observer;




//import junit.framework.Test;
import org.junit.Test;							//new imports
import static org.junit.Assert.assertEquals;	//new imports
//import junit.framework.TestCase;
//import junit.framework.TestSuite;
import org.junit.runner.JUnitCore;				//new imports
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;	//new imports



import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

interface Subject {
 public int addObserver(Observer o);
 public void removeObserver(Observer o);
 public String getState();
 public void setState(String state);
}

interface Observer {
 public void update(Subject o);
}

class ObserverImpl implements Observer {
 private String state = "";

 public void update(Subject o) {
   state = o.getState();
   System.out.println("Update received from Subject and state is changed to : ");
   System.out.println(state);
 }
}

class SubjectImpl implements Subject {
 private List observers = new ArrayList();

 private String state = "";

 public String getState() {
   return state;
 }

 public void setState(String state) {
   this.state = state;
   notifyObservers();
 }

 public int addObserver(Observer o) {
   observers.add(o);
   System.out.println("Observer is added...");
   int i;
   i=observers.size();
   return i;
 }

 public void removeObserver(Observer o) {
   observers.remove(o);
   System.out.println("Observer is removed...");
  }

 public void notifyObservers() {
   Iterator i = observers.iterator();
   while (i.hasNext()) {
     Observer o = (Observer) i.next();
     o.update(this);
   }
 }
}

public class ObserverPattern /**extends TestCase**/ {

 public static void main(String[] args) {
   Observer o = new ObserverImpl();
   Subject s = new SubjectImpl();
   int k;
   k=s.addObserver(o);
   s.setState("New State");

 }


//public static Test suite(){
//	return new TestSuite(ObserverPattern.class);
//	}
 
@Test
 public void testAddObserver(){                                                 //JUnit Test class
	Observer testObserver = new ObserverImpl();
	Subject testSubject = new SubjectImpl();
	assertEquals(1,testSubject.addObserver(testObserver));
 }

public class ObserverTestRunner {
	
	public void main(String[] args) {
	      
		Result result = JUnitCore.runClasses(ObserverPattern.class);
	      for (Failure failure : result.getFailures()) {
	          System.out.println(failure.toString());
	       }
	       System.out.println(result.wasSuccessful());
	}
}
}