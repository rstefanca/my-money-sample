package cz.codingmonkey.domain;

import org.joda.time.YearMonth;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.Assert.*;

/**
 * @author rstefanca
 */
public class InterestSummaryTest {

	@Test
	public void testClassInitialization() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);
		assertEquals(yearMonth, interestSummary.getYearMonth());
		assertEquals(BigDecimal.ZERO, interestSummary.getAssetsTotal());
		assertEquals(BigDecimal.ZERO, interestSummary.getLiabilitiesTotal());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAddInterestFromDifferentYearMonthException() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);
		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth.plusMonths(-1))));
	}

	@Test
	public void shouldNotAddInterestFromDifferentYearMonth() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);
		assertFalse(interestSummary.addInterestMatchingYearMonth(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth.plusMonths(-1)))));
	}

	@Test
	public void shouldSumAssetsAndLiabilities() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);

		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));
		interestSummary.addInterest(new Liability("test", ONE, ValidityInterval.fromYearMonth(yearMonth)));

		assertEquals(TEN, interestSummary.getAssetsTotal());
		assertEquals(ONE, interestSummary.getLiabilitiesTotal());
	}

	@Test
	public void balanceShouldBeZero() {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);

		interestSummary.addInterest(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));
		interestSummary.addInterest(new Liability("test", TEN, ValidityInterval.fromYearMonth(yearMonth)));

		assertEquals(BigDecimal.ZERO, interestSummary.getBalance());
	}

	@Test
	public void shouldAddInterstMathcingYearMonth() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);

		assertTrue(interestSummary.addInterestMatchingYearMonth(new Asset("test", TEN, ValidityInterval.fromYearMonth(yearMonth))));

	}

	@Test(expected = IllegalArgumentException.class)
	public void sumAssetsAndLiabilitiesShouldThrowExceptionForUnknownInterest() throws Exception {
		YearMonth yearMonth = YearMonth.now();
		InterestSummary interestSummary = InterestSummary.forYearMonth(yearMonth);
		interestSummary.addInterest(new TestInterest());

	}



	private class TestInterest extends Interest {

		TestInterest() {
			super("", BigDecimal.TEN, ValidityInterval.fromDate(new Date()));
		}

		@Override
		public BigDecimal getValue() {
			throw new UnsupportedOperationException();
		}
	}


}