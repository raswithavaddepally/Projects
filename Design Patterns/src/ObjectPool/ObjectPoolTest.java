package ObjectPool;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;

import org.junit.Test;

public class ObjectPoolTest {
	
	@Test(expected = PoolFullException.class)
	public void testForPoolFull() throws PoolFullException{
		JDBCConnectionPool pool = new JDBCConnectionPool("ooad",
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname",
				"sa", "secret");

		// Get a book
		Connection con = pool.checkOut();
		Connection con1 = pool.checkOut();
		//Exception for the third connection
		Connection con2 = pool.checkOut();
		
	}
	@Test
	public void testForReuse() throws PoolFullException{
		JDBCConnectionPool pool = new JDBCConnectionPool("ooad",
				"org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname",
				"sa", "secret");

		Connection con = pool.checkOut();
		
	
		assertEquals(con,con);
	}
	
}
