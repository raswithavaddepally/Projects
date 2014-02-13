package ObjectPool;
import java.sql.Connection;



public class Main {

	/* This program demonstrates object pool pattern with an example of library. A person can checkout
	 * a book from library and can checkin it. As of the implementation is considered, only one book 
	 * with multiple copies are available. Each copy is a object. 
	 */
	public static void main(String[] args) throws ClassNotFoundException, PoolFullException {
		// TODO Auto-generated method stub


		// Create the ConnectionPool to the library to get a book
		//Class.forName("org.hsqldb.jdbcDriver");
		JDBCConnectionPool pool = new JDBCConnectionPool("ooad",
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname",
				"sa", "secret");

		// Get a book
		Connection con = pool.checkOut();

		


		// Return the book
		pool.checkIn(con);
		
		//Multiple checkout and checkin statements to show implementation of object pool.
		con = pool.checkOut();
		pool.checkIn(con);
		con = pool.checkOut();
		
	pool.checkIn(con);
	con = pool.checkOut();
	
	con = pool.checkOut();
	con= pool.checkOut();
		

	}

}
