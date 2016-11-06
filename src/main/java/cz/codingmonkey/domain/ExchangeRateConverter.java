package cz.codingmonkey.domain;

/**
 * @author Richard Stefanca
 */
@FunctionalInterface
public interface ExchangeRateConverter {

	MoneyAmount compute(ExchangeRateComputationContext moneyAmount);

}
