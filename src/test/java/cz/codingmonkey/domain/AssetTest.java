package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author rstefanca
 */
public class AssetTest {

	@Test
	public void valueShouldBePositive() throws Exception {
		Asset asset = new Asset("name", BigDecimal.TEN);
		assertEquals(BigDecimal.TEN, asset.getValue());
	}
}