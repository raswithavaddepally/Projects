package builder;

public class ChocalateCakeBuilder extends CakeBuilder{

	/**
	 * Builds the batter for a ChocalateCake.
	 */
	@Override
	public void buildBatter() {
		cake.setBatter("chocalateCakeBatter");
	}

	/**
	 * Builds the icing for a ChocalateCake.
	 */
	@Override
	public void buildIcing() {
		cake.setIcing("chocalateCakeIcing");
	}

	/**
	 * Builds the transmission for a ChocalateCake.
	 */
	@Override
	public void buildTransmission() {
		cake.setTransmission("chocalateCakeTransmission");
	}

}
