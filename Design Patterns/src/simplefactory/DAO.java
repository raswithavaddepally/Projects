package simplefactory;


public interface DAO<T> {

	public void insert(T value);
    public void retrieve(int id);
   //  public void delete(int custId);
 	//public void update(T value);
   //  public void delete(T value);
}

