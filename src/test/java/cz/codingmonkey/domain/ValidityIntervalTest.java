package cz.codingmonkey.domain;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author rstefanca
 */
public class ValidityIntervalTest {

	private static final LocalDate NOW = LocalDate.now();

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowToBeforeFrom() {
		new ValidityInterval(NOW.plusDays(1), NOW);
	}

	@Test
	public void shouldBeValidOnActualDate() {
		ValidityInterval validityInterval = ValidityInterval.fromDate(DateTime.now().withTimeAtStartOfDay().toDate());
		YearMonth actual = YearMonth.now();
		assertTrue(validityInterval.isValidInYearMonth(actual));
	}

	@Test
	public void shouldBeValidNextMonth() {
		ValidityInterval validityInterval = ValidityInterval.fromDate(NOW);
		YearMonth nextMonth = YearMonth.now().plusMonths(1);
		assertTrue(validityInterval.isValidInYearMonth(nextMonth));
	}

	@Test
	public void shouldNotBeValidWhenBefore() {
		ValidityInterval validityInterval = new ValidityInterval(LocalDate.now());
		YearMonth before = YearMonth.now().plusMonths(-1);

		assertFalse(validityInterval.isValidInYearMonth(before));
	}

	@Test
	public void shouldNotBeValidWhenAfter() {
		ValidityInterval validityInterval = ValidityInterval.fromYearMonth(YearMonth.now());
		YearMonth after = YearMonth.now().plusMonths(1);
		assertFalse(validityInterval.isValidInYearMonth(after));
	}

	@Test
	public void yearMonthWithin() {
		ValidityInterval validityInterval = ValidityInterval.closed(
				DateUtils.truncate(NOW.toDate(), Calendar.YEAR),
				DateUtils.ceiling(NOW.toDate(), Calendar.YEAR));
		YearMonth actual = YearMonth.now();

		assertTrue(validityInterval.isValidInYearMonth(actual));
	}

	@Test
	public void equalIntervalAndYearMonth() {
		ValidityInterval validityInterval = ValidityInterval.fromYearMonth(YearMonth.now());
		assertTrue(validityInterval.isValidInYearMonth(YearMonth.now()));
	}
}