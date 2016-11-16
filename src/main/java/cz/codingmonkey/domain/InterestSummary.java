package cz.codingmonkey.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.joda.time.YearMonth;

import java.math.BigDecimal;

/**
 * @author rstefanca
 */
@ToString(exclude = "exchangeRateConverter")
public class InterestSummary {

	private final ExchangeRateConverter exchangeRateConverter;

	@Getter
	private final YearMonth yearMonth;

	@Getter
	private final String targetCurrency;

	@Getter
	private BigDecimal assetsTotal = BigDecimal.ZERO;

	@Getter
	private BigDecimal liabilitiesTotal = BigDecimal.ZERO;

	private InterestSummary(@NonNull YearMonth yearMonth, @NonNull String targetCurrency, @NonNull ExchangeRateConverter exchangeRateConverter) {
		this.yearMonth = yearMonth;
		this.targetCurrency = targetCurrency;
		this.exchangeRateConverter = exchangeRateConverter;
	}

	public static InterestSummary forYearMonth(YearMonth yearMonth, String targetCurrency, ExchangeRateConverter exchangeRateConverter) {
		return new InterestSummary(yearMonth, targetCurrency, exchangeRateConverter);
	}

	public void addInterest(@NonNull Interest interest) {
		if (!interest.isValidInYearMonth(yearMonth)) {
			throw new IllegalArgumentException("Cannot add interest that is not valid in " + yearMonth);
		}

		addInterestInternal(interest);
	}

	public boolean addInterestMatchingYearMonth(@NonNull Interest interest) {
		if (interest.isValidInYearMonth(yearMonth)) {
			addInterestInternal(interest);
			return true;
		}

		return false;
	}

	public BigDecimal getBalance() {
		return liabilitiesTotal.negate().add(assetsTotal);
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
}