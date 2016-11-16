package cz.codingmonkey.domain;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;


/**
 * Represents amount of money in given currency
 *
 * @author rstefanca
 */
@Value
@AllArgsConstructor
public class MoneyAmount {

	@NonNull
	private final BigDecimal amount;
	@NonNull
	private final String currency;

	public MoneyAmount negate() {
		return new MoneyAmount(amount.negate(), currency);
	}

}
