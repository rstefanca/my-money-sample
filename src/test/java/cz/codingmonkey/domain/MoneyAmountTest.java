package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author Richard Stefanca
 */
public class MoneyAmountTest {

	@Test
	public void shouldReturnNegativeValue() throws Exception {
		MoneyAmount czk = new MoneyAmount(BigDecimal.ONE, "CZK");
		assertEquals(new MoneyAmount(BigDecimal.ONE.negate(), "CZK"), czk.negate());
	}

	@Test
	public void shouldBeEqual() throws Exception {
		assertEquals(new MoneyAmount(BigDecimal.ONE, "CZK"), new MoneyAmount(BigDecimal.ONE, "CZK"));
	}

	@Test
	public void shouldNotEqual() throws Exception {
		assertNotEquals(new MoneyAmount(BigDecimal.ONE, "CZK"), new MoneyAmount(BigDecimal.ONE, "GEL"));
	}
}