package builder;

public abstract class CakeBuilder {
	//current cake of this builder
	protected Cake cake;
	
	/**
	 * Return the current cake of this builder.
	 * @return Cake
	 */
	public Cake getCake(){
		return cake;
	}
	
	/**
	 * Creates and stores a new cake
	 */
	public void createNewCake(){
		cake = new Cake();
	}
	
	/**
	 * Build the batter of this cake.
	 */
	public abstract void buildBatter();
	/**
	 * Build the icing of this cake.
	 */
	public abstract void buildIcing();
	/**
	 * Build the transmission of this car.
	 */
	public abstract void buildTransmission();

}
