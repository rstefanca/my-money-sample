package cz.codingmonkey.domain;

import lombok.Getter;
import lombok.NonNull;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.math.BigDecimal;

/**
 * @author rstefanca
 */
public abstract class Interest {

	@Getter
	protected final String name;

	@Getter
	protected final ValidityInterval validityInterval;

	@Getter
	final MoneyAmount moneyAmount;

	Interest(String name, MoneyAmount moneyAmount) {
		this(name, moneyAmount, new ValidityInterval(LocalDate.now()));
	}

	Interest(@NonNull String name,@NonNull MoneyAmount moneyAmount,@NonNull ValidityInterval validityInterval) {
		this.name = name;
		if (moneyAmount.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("An moneyAmount must be positive value greater than zero. Was " + moneyAmount);
		}
		this.moneyAmount = moneyAmount;
		this.validityInterval = validityInterval;
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
