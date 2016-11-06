package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author rstefanca
 */
public class AssetTest {

	private static final MoneyAmount TEN = new MoneyAmount(BigDecimal.TEN, "CZK");

	@Test
	public void valueShouldBePositive() throws Exception {
		Asset asset = new Asset("name", TEN);
		assertEquals(TEN, asset.getRealValue());
	}
}