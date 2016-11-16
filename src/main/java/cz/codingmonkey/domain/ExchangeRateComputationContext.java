package cz.codingmonkey.domain;

import lombok.NonNull;
import lombok.Value;
import org.joda.time.YearMonth;


/**
 * @author Richard Stefanca
 */

@Value
public class ExchangeRateComputationContext {

	@NonNull private final YearMonth yearMonth;
	@NonNull private final MoneyAmount moneyAmount;
	@NonNull private final String targetCurrency;

}
