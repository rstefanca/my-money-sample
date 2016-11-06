package cz.codingmonkey.domain;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author rstefanca
 */
public class InterestCollectionTest {

	private static final MoneyAmount TEN = new MoneyAmount(BigDecimal.TEN, "CZK");

	@Test
	public void sumShouldBeZero() {
		Liability liability = new Liability("test", TEN);
		Asset asset = new Asset("test", TEN);

		InterestCollection collection = new InterestCollection(Arrays.asList(
				liability,
				asset)
		);

		assertEquals(BigDecimal.ZERO, collection.sum());
	}

}