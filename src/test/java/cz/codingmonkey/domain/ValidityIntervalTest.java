package cz.codingmonkey.domain;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
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

	private static final Date NOW = new Date();

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowToBeforeFrom() {
		new ValidityInterval(DateUtils.addDays(NOW, 1), NOW);
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
		ValidityInterval validityInterval = new ValidityInterval(DateTime.now().withTimeAtStartOfDay().toDate());
		YearMonth before = YearMonth.fromDateFields(DateUtils.addMonths(NOW, -1));

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
		ValidityInterval validityInterval = new ValidityInterval(DateUtils.truncate(NOW, Calendar.YEAR), DateUtils.ceiling(NOW, Calendar.YEAR));
		YearMonth actual = YearMonth.now();

		assertTrue(validityInterval.isValidInYearMonth(actual));
	}

	@Test
	public void equalIntervalAndYearMonth() {
		ValidityInterval validityInterval = ValidityInterval.fromYearMonth(YearMonth.now());
		assertTrue(validityInterval.isValidInYearMonth(YearMonth.now()));
	}
}