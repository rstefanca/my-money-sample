package cz.codingmonkey.domain;

import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author rstefanca
 */
public class InterestSummary {

	private final YearMonth yearMonth;
	private BigDecimal assetsTotal = BigDecimal.ZERO;
	private BigDecimal liabilitiesTotal = BigDecimal.ZERO;

	protected InterestSummary(YearMonth yearMonth) {
		this.yearMonth = Objects.requireNonNull(yearMonth, "yearMonth");
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

	public static InterestSummary forYearMonth(YearMonth yearMonth) {
		return new InterestSummary(yearMonth);
	}

	public void addInterest(Interest interest) {
		Objects.requireNonNull(interest, "interest");
		if (!interest.isValidInYearMonth(yearMonth)) {
			throw new IllegalArgumentException("Cannot add interest that is not valid in " + yearMonth);
		}

		addInterestInternal(interest);
	}

	public boolean addInterestMatchingYearMonth(Interest interest) {
		Objects.requireNonNull(interest, "interest");
		if (interest.isValidInYearMonth(yearMonth)) {
			addInterestInternal(interest);
			return true;
		}

		return false;
	}

	private void addInterestInternal(Interest interest) {
		if (interest instanceof Asset) {
			assetsTotal = assetsTotal.add(interest.getAmount());
		} else if (interest instanceof Liability) {
			liabilitiesTotal = liabilitiesTotal.add(interest.getAmount());
		} else {
			throw new IllegalArgumentException("Unsupported interest type " + interest.getClass().getName());
		}
	}

	@Override
	public String toString() {
		return "InterestSummary{" +
				"yearMonth=" + yearMonth +
				", assetsTotal=" + assetsTotal +
				", liabilitiesTotal=" + liabilitiesTotal +
				", balance=" + getBalance() +
				'}';
	}
}
