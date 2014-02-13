package singleton;
public class Singleton {
	private static Singleton instance = new Singleton();
	 
    private Singleton() {
    }
 
    public static synchronized Singleton getInstance() {
        return instance;
    }
 
    public void fibonacci (int n) {
      
        for(int j=1;j< n; j++)
        {
        	System.out.print(fib(j) + ", ");
        }
    }
    
    static int fib(int n) {
        if (n <= 2) 
             return 1;

        return fib(n-1) + fib(n-2);
   } 
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Clone is not allowed.");
    }

}
