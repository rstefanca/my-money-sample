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

	final MoneyAmount moneyAmount;

	Interest(String name, MoneyAmount moneyAmount) {
		this(name, moneyAmount, new ValidityInterval(new Date()));
	}

	Interest(String name, MoneyAmount moneyAmount, ValidityInterval validityInterval) {
		this.name = requireNonNull(name, "name");
		if (requireNonNull(moneyAmount, "moneyAmount").getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("An moneyAmount must be positive value greater than zero. Was " + moneyAmount);
		}
		this.moneyAmount = moneyAmount;
		this.validityInterval = requireNonNull(validityInterval, "validityInterval");
	}

	public String getName() {
		return name;
	}

	public ValidityInterval getValidityInterval() {
		return validityInterval;
	}

	public MoneyAmount getMoneyAmount() {
		return moneyAmount;
	}

	final boolean isValidInYearMonth(YearMonth yearMonth) {
		return validityInterval.isValidInYearMonth(yearMonth);
	}

	@Override
	public String toString() {
		return "Interest{" +
				"name='" + name + '\'' +
				", validityInterval=" + validityInterval +
				", moneyAmount=" + moneyAmount +
				'}';
	}

	/**
	 * Returns real value of the interest
	 *
	 * @return real value of the interest
	 */
	public abstract MoneyAmount getRealValue();
}
