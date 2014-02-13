package builder;

public class PineappleCakeBuilder extends CakeBuilder{

	/**
	 * Builds the batter for a PineappleCake.
	 */
	@Override
	public void buildBatter() {
		cake.setBatter("pineappleCakeBatter");
	}

	/**
	 * Builds the icing for a PineappleCake.
	 */
	@Override
	public void buildIcing() {
		cake.setIcing("pineappleCakeIcing");
	}

	/**
	 * Builds the transmission for a PineappleCake.
	 */
	@Override
	public void buildTransmission() {
		cake.setTransmission("pineappleCakeTransmission");
	}
	
}
