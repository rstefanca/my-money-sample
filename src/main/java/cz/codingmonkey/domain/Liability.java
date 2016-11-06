package cz.codingmonkey.domain;

/**
 * @author rstefanca
 */
public class Liability extends Interest {

	public Liability(String name, MoneyAmount amount) {
		super(name, amount);
	}

	public Liability(String name, MoneyAmount amount, ValidityInterval validityInterval) {
		super(name, amount, validityInterval);
	}

	public final MoneyAmount getRealValue() {
		return moneyAmount.negate();
	}

	@Override
	public String toString() {
		return "Liability{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", moneyAmount=" + moneyAmount +
				'}';
	}
}
