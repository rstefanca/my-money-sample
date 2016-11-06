package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author rstefanca
 */
public class LiabilityTest {

	private static final MoneyAmount TEN = new MoneyAmount(BigDecimal.TEN, "CZK");

	@Test
	public void valueShouldBeNegative() {
		Liability liability = new Liability("liability", TEN);
		assertEquals(new MoneyAmount(BigDecimal.TEN.negate(), "CZK"), liability.getRealValue());
	}

}