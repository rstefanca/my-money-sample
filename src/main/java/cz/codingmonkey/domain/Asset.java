package cz.codingmonkey.domain;

import java.math.BigDecimal;

/**
 * @author rstefanca
 */
public class Asset extends Interest {

	public Asset(String name, BigDecimal amount) {
		super(name, amount);
	}

	public Asset(String test, BigDecimal ten, ValidityInterval validityInterval) {
		super(test, ten, validityInterval);
	}

	public final BigDecimal getValue() {
		return amount;
	}

	@Override
	public String toString() {
		return "Asset{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", amount=" + amount +
				'}';
	}


}
