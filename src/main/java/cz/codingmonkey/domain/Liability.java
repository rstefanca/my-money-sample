package cz.codingmonkey.domain;

import java.math.BigDecimal;

/**
 * @author rstefanca
 */
public class Liability extends Interest {

	public Liability(String name, BigDecimal amount) {
		super(name, amount);
	}

	public Liability(String name, BigDecimal amount, ValidityInterval validityInterval) {
		super(name, amount, validityInterval);
	}

	public final BigDecimal getValue() {
		return amount.negate();
	}

	@Override
	public String toString() {
		return "Liability{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", amount=" + amount +
				'}';
	}
}
