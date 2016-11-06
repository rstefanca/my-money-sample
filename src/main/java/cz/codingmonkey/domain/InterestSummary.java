package cz.codingmonkey.domain;

import org.joda.time.YearMonth;

import java.math.BigDecimal;

import static java.util.Objects.*;

/**
 * @author rstefanca
 */
public class InterestSummary {

	private final YearMonth yearMonth;
	private final String targetCurrency;
	private final ExchangeRateConverter exchangeRateConverter;
	private BigDecimal assetsTotal = BigDecimal.ZERO;
	private BigDecimal liabilitiesTotal = BigDecimal.ZERO;

	private InterestSummary(YearMonth yearMonth, String targetCurrency, ExchangeRateConverter exchangeRateConverter) {
		this.yearMonth = requireNonNull(yearMonth, "yearMonth");
		this.targetCurrency = requireNonNull(targetCurrency, "targetCurrency");
		this.exchangeRateConverter = requireNonNull(exchangeRateConverter, "exchangeRateConverter");
	}

	public static InterestSummary forYearMonth(YearMonth yearMonth, String targetCurrency, ExchangeRateConverter exchangeRateConverter) {
		return new InterestSummary(yearMonth, targetCurrency, exchangeRateConverter);
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public BigDecimal getAssetsTotal() {
		return assetsTotal;
	}

	public BigDecimal getLiabilitiesTotal() {
		return liabilitiesTotal;
	}

	public BigDecimal getBalance() {
		return liabilitiesTotal.negate().add(assetsTotal);
	}

	public void addInterest(Interest interest) {
		requireNonNull(interest, "interest");
		if (!interest.isValidInYearMonth(yearMonth)) {
			throw new IllegalArgumentException("Cannot add interest that is not valid in " + yearMonth);
		}

		addInterestInternal(interest);
	}

	public boolean addInterestMatchingYearMonth(Interest interest) {
		requireNonNull(interest, "interest");
		if (interest.isValidInYearMonth(yearMonth)) {
			addInterestInternal(interest);
			return true;
		}

		return false;
	}

	private void addInterestInternal(Interest interest) {
		BigDecimal amount = computeAmountToTargetCurrency(interest);
		if (interest instanceof Asset) {
			assetsTotal = assetsTotal.add(amount);
		} else if (interest instanceof Liability) {
			liabilitiesTotal = liabilitiesTotal.add(amount);
		} else {
			throw new IllegalArgumentException("Unsupported interest type " + interest.getClass().getName());
		}
	}

	private BigDecimal computeAmountToTargetCurrency(Interest interest) {
		if (!targetCurrency.equals(interest.getMoneyAmount().getCurrency())) {
			ExchangeRateComputationContext ctx = createExchangeRateComputationContext(interest);
			return exchangeRateConverter
					.compute(ctx)
					.getAmount();
		} else {
			return interest.getMoneyAmount().getAmount();
		}
	}

	private ExchangeRateComputationContext createExchangeRateComputationContext(Interest interest) {
		return new ExchangeRateComputationContext(yearMonth, interest.getMoneyAmount(), targetCurrency);
	}

	@Override
	public String toString() {
		return "InterestSummary{" +
				"yearMonth=" + yearMonth +
				", targetCurrency='" + targetCurrency + '\'' +
				", assetsTotal=" + assetsTotal +
				", liabilitiesTotal=" + liabilitiesTotal +
				'}';
	}
}
