package ObjectPool;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;


public abstract class ObjectPool<T> {
	int i;
	int j;
	int max_no_of_objects=2; //Total number of objects that can be used
	private long expirationTime;

	private Hashtable<T, Long> locked, unlocked;

	public ObjectPool() {
		expirationTime = 30; // 30 seconds
		locked = new Hashtable<T, Long>();
		unlocked = new Hashtable<T, Long>();
	}
	public String name;
	protected abstract T book();

	public abstract boolean check_availability(T o);

	public abstract void expire(T o);
	// TODO checkout a book from the library
	public synchronized T checkOut() throws PoolFullException {

		long now = System.currentTimeMillis();
		T t;
		if (unlocked.size() > 0) {
			Enumeration<T> e = unlocked.keys();
			while (e.hasMoreElements()) {
				t = e.nextElement();
				if ((now - unlocked.get(t)) > expirationTime) {
					// object has expired
					unlocked.remove(t);
					expire(t);
					t = null;
				} else {
					if (check_availability(t)) {
						unlocked.remove(t);
						locked.put(t, now);
						System.out.println("Returns book to library " + this.name);
						j++;
						//System.out.println("j value=" + j);
						return (t);
					} else {
						// object failed validation
						unlocked.remove(t);
						expire(t);
						t = null;
					}
				}
			}
		}
		// Creation of objects
		System.out.println();

		t = book();
		locked.put(t, now);
		//System.out.println("unlock=" + j);

		i=locked.values().size() - j;
		//System.out.println("value of i" +i);
		if(i<max_no_of_objects)
		{

			System.out.println("Checkout book " + this.name);
			System.out.println("Num of books checkout so far :: " + locked.values().size() );
			//System.out.println("Num of books free  :: " + unlocked.values().size() );
			return (t);
		}
		else{
			throw new PoolFullException();
		}


	}

	public synchronized void checkIn(T t) throws PoolFullException {
		locked.remove(t);
		unlocked.put(t, System.currentTimeMillis());
		//System.out.println("ready to use" + unlocked.values().size());
		//j = unlocked.values().size();
		checkOut();
	}
}

