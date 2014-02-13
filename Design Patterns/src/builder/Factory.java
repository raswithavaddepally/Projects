package builder;

public class Factory {
	
	private CakeBuilder cakeBuilder = null;

	public void setCakeBuilder(CakeBuilder cb) {
		cakeBuilder = cb;
	}
	
	public Cake getCake(){
		return cakeBuilder.getCake();
	}
	
	public void constructCake(){
		cakeBuilder.createNewCake();
		cakeBuilder.buildBatter();
		cakeBuilder.buildIcing();
		cakeBuilder.buildTransmission();
	}

}
