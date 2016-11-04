package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author rstefanca
 */
public class LiabilityTest {

	@Test
	public void valueShouldBeNegative() {
		Liability liability = new Liability("liability", BigDecimal.TEN);
		assertEquals(BigDecimal.TEN.negate(), liability.getValue());
	}

}