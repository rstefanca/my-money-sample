package cz.codingmonkey.domain;

import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.Date;

import static java.util.Objects.requireNonNull;

/**
 * @author rstefanca
 */
public abstract class Interest {

	protected final String name;

	protected final ValidityInterval validityInterval;

	final BigDecimal amount;

	Interest(String name, BigDecimal amount) {
		this(name, amount, new ValidityInterval(new Date()));
	}

	Interest(String name, BigDecimal amount, ValidityInterval validityInterval) {
		this.name = requireNonNull(name, "name");
		if (requireNonNull(amount, "amount").compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("An amount must be positive value greater than zero. Was " + amount);
		}
		this.amount = amount;
		this.validityInterval = requireNonNull(validityInterval, "validityInterval");
	}

	@Override
	public String toString() {
		return "Interest{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", amount=" + amount +
				'}';
	}

	public String getName() {
		return name;
	}

	public ValidityInterval getValidityInterval() {
		return validityInterval;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	final boolean isValidInYearMonth(YearMonth yearMonth) {
		return validityInterval.isValidInYearMonth(yearMonth);
	}

	public abstract BigDecimal getValue();
}
