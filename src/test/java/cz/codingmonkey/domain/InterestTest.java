package cz.codingmonkey.domain;

import org.joda.time.YearMonth;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author rstefanca
 */
public class InterestTest {

	private static final String ANY_NAME = "name";
	private static final Date NOW = new Date();
	private static final MoneyAmount TEN = new MoneyAmount(BigDecimal.TEN, "CZK");
	private static final MoneyAmount ZERO = new MoneyAmount(BigDecimal.ZERO, "CZK");

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowNegativeValues() {
		new TestedInterest(ANY_NAME, TEN.negate(), ValidityInterval.fromDate(NOW));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAllowZeroValue() {
		new TestedInterest(ANY_NAME, ZERO, ValidityInterval.fromDate(NOW));
	}

	@Test(expected = NullPointerException.class)
	public void shouldRequireName() {
		new TestedInterest(null, ZERO, ValidityInterval.fromDate(NOW));
	}

	@Test(expected = NullPointerException.class)
	public void shouldRequireAmount() {
		new TestedInterest(ANY_NAME, null, ValidityInterval.fromDate(NOW));
	}

	@Test(expected = NullPointerException.class)
	public void shouldRequireValidity() {
		new TestedInterest(ANY_NAME, TEN, null);
	}

	@Test
	public void testClassInitialization() {
		ValidityInterval expectedValidityInterval = ValidityInterval.fromDate(NOW);
		TestedInterest testedInterest = new TestedInterest(ANY_NAME, TEN, expectedValidityInterval);
		assertEquals(ANY_NAME, testedInterest.getName());
		assertEquals(TEN, testedInterest.getMoneyAmount());
		assertEquals(expectedValidityInterval, testedInterest.getValidityInterval());
	}

	@Test
	public void shouldCallValidityIntervalIsValidInterval() {
		ValidityInterval validityIntervalMock = mock(ValidityInterval.class);
		TestedInterest testedInterest = new TestedInterest(ANY_NAME, TEN, validityIntervalMock);
		YearMonth expectedYearMonth = YearMonth.now();
		when(validityIntervalMock.isValidInYearMonth(eq(expectedYearMonth))).thenReturn(true);

		assertTrue(testedInterest.isValidInYearMonth(expectedYearMonth));
		verify(validityIntervalMock).isValidInYearMonth(expectedYearMonth);
	}

	private class TestedInterest extends Interest {

		TestedInterest(String name, MoneyAmount amount, ValidityInterval validityInterval) {
			super(name, amount, validityInterval);
		}

		public MoneyAmount getRealValue() {
			return null;
		}
	}


}