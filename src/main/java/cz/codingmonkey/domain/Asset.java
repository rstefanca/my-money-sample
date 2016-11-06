package cz.codingmonkey.domain;

/**
 * @author rstefanca
 */
public class Asset extends Interest {

	public Asset(String name, MoneyAmount amount) {
		super(name, amount);
	}

	public Asset(String test, MoneyAmount ten, ValidityInterval validityInterval) {
		super(test, ten, validityInterval);
	}

	public final MoneyAmount getRealValue() {
		return moneyAmount;
	}

	@Override
	public String toString() {
		return "Asset{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", moneyAmount=" + moneyAmount +
				'}';
	}


}
