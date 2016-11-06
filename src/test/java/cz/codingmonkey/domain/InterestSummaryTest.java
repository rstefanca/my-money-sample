package cz.codingmonkey.domain;

import org.joda.time.YearMonth;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;


import static org.junit.Assert.*;

/**
 * @author rstefanca
 */
public class InterestSummaryTest {

	//return the same value
	private static final ExchangeRateConverter SIMPLE_EXCHANGE_RATE_CONVERTER = ExchangeRateComputationContext::getMoneyAmount;

	public static final String CZK = "CZK";

	private static final MoneyAmount TEN = new MoneyAmount(BigDecimal.TEN, CZK);
	private static final MoneyAmount ZERO = new MoneyAmount(BigDecimal.ZERO, "CZK");
	private static final MoneyAmount ONE = new MoneyAmount(BigDecimal.ONE, "CZK");

	@Test
	public void testClassInitialization() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);
		assertEquals(yearMonth, interestSummary.getYearMonth());
		assertEquals(BigDecimal.ZERO, interestSummary.getAssetsTotal());
		assertEquals(BigDecimal.ZERO, interestSummary.getLiabilitiesTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddInterestFromDifferentYearMonthException() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);
		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth.plusMonths(-1))));
	}

	@Test
	public void shouldNotAddInterestFromDifferentYearMonth() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);
		assertFalse(interestSummary.addInterestMatchingYearMonth(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth.plusMonths(-1)))));
	}

	@Test
	public void shouldSumAssetsAndLiabilities() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);

		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));
		interestSummary.addInterest(new Liability("test", ONE, ValidityInterval.fromYearMonth(yearMonth)));

		assertEquals(BigDecimal.TEN, interestSummary.getAssetsTotal());
		assertEquals(BigDecimal.ONE, interestSummary.getLiabilitiesTotal());
	}

	@Test
	public void balanceShouldBeZero() {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);

		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));
		interestSummary.addInterest(new Liability("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));

		assertEquals(BigDecimal.ZERO, interestSummary.getBalance());
	}

	@Test
	public void shouldAddInterestMatchingYearMonth() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);

		assertTrue(interestSummary.addInterestMatchingYearMonth(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void sumAssetsAndLiabilitiesShouldThrowExceptionForUnknownInterest() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, CZK, SIMPLE_EXCHANGE_RATE_CONVERTER);
		interestSummary.addInterest(new TestInterest());
	}

	@Test
	public void shouldCalcluteToTargetCurrency() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		ExchangeRateConverter converter = ctx -> new MoneyAmount(ctx.getMoneyAmount().getAmount().multiply(BigDecimal.TEN), "USD");
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth, "USD", converter);
		interestSummary.addInterest(new Liability("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));

		assertEquals(BigDecimal.valueOf(100), interestSummary.getLiabilitiesTotal());
	}

	private class TestInterest extends Interest {

		TestInterest() {
			super("", TEN, ValidityInterval.fromDate(new Date()));
		}

		@Override
		public MoneyAmount getRealValue() {
			throw new UnsupportedOperationException();
		}
	}
}