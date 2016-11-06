package cz.codingmonkey.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.*;

/**
 * Represents moneyAmount of money in given currency
 *
 * @author rstefanca
 */
public class MoneyAmount {

	private final BigDecimal amount;
	private final String currency;

	public MoneyAmount(BigDecimal amount, String currency) {
		this.amount = requireNonNull(amount, "moneyAmount");
		this.currency = requireNonNull(currency, "currency");
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public MoneyAmount negate() {
		return new MoneyAmount(amount.negate(), currency);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MoneyAmount that = (MoneyAmount) o;
		return Objects.equals(amount, that.amount) &&
				Objects.equals(currency, that.currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency);
	}

	@Override
	public String toString() {
		return "MoneyAmount{" +
				"amount=" + amount +
				", currency='" + currency + '\'' +
				'}';
	}
}
