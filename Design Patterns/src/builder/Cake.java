package builder;

public class Cake {

	//type of batter used for this cake
	private String batter = "";
	//type of icing used for this cake
	private String icing = "";
	//type of transmission used for this cake
	private String transmission = "";

	/**
	 * Get the type of batter for the car.
	 * @return batter
	 */
	public String getBatter(){
		return batter;
	}
	/**
	 * Set the type of batter for this cake.
	 * @param b
	 */
	public void setBatter(String b){
		batter = b;
	}
	/**
	 * Get the type of icing for the cake.
	 * @return icing
	 */
	public String getIcing(){
		return icing;
	}
	/**
	 * Set the type of icing for this cake.
	 * @param i
	 */
	public void setIcing(String i){
		icing = i;
	}
	/**
	 * Get the type of transmission for the cake.
	 * @return Transmission
	 */
	public String getTransmission(){
		return transmission;
	}
	/**
	 * Set the type of transmission for this cake.
	 * @param t
	 */
	public void setTransmission(String t){
		transmission = t;
	}

}
