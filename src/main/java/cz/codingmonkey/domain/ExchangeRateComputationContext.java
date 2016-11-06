package cz.codingmonkey.domain;


import org.joda.time.YearMonth;

import java.util.Objects;

import static java.util.Objects.*;

/**
 * @author Richard Stefanca
 */
public class ExchangeRateComputationContext {

	private final YearMonth yearMonth;
	private final MoneyAmount moneyAmount;
	private final String targetCurrency;

	public ExchangeRateComputationContext(YearMonth yearMonth, MoneyAmount moneyAmount, String targetCurrency) {
		this.yearMonth = requireNonNull(yearMonth, "yearMonth");
		this.moneyAmount = requireNonNull(moneyAmount, "moneyAmount");
		this.targetCurrency = requireNonNull(targetCurrency, "targetCurrency");
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public MoneyAmount getMoneyAmount() {
		return moneyAmount;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}
}
