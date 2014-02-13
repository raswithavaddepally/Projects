
package javarmi;

import java.io.Serializable;


public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6769285029905373070L;

	private int custid;

	private String name;
	private int age;
	private String sex;
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}

