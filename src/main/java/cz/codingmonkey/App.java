package cz.codingmonkey;

import cz.codingmonkey.domain.*;
import org.joda.time.Period;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static cz.codingmonkey.domain.InterestSummary.forYearMonth;
import static java.util.stream.Collectors.*;

/**
 * @author rstefanca
 */
public class App {

	public static void main(String[] args) {
		Random random = new Random();

		Stream<YearMonth> last12MonthsStream = IntStream.range(-12, 1)
				.mapToObj(i -> YearMonth.now().plusMonths(i));

		List<Interest> interests = getInterests(random);
		interests.forEach(System.out::println);

		ExchangeRateConverter exchangeRateConverter = getExchangeRateConverter();

		last12MonthsStream
				.map(yearMonth -> forYearMonth(yearMonth, "USD", exchangeRateConverter))
				.map(interestSummary -> addInterestToSummary(interests, interestSummary))
				.forEach(System.out::println);
	}

	private static InterestSummary addInterestToSummary(List<Interest> interests, InterestSummary interestSummary) {
		List<Interest> added = interests.stream()
				.filter(interestSummary::addInterestMatchingYearMonth)
				.collect(toList());
		interests.removeAll(added);
		return interestSummary;
	}

	private static ExchangeRateConverter getExchangeRateConverter() {
		return ctx -> {
			BigDecimal amount = ctx.getMoneyAmount().getAmount();
			BigDecimal exchangeRate = ctx.getYearMonth().isBefore(YearMonth.now().plusMonths(6))
					? new BigDecimal("27.06") : new BigDecimal("27.02");
			return new MoneyAmount(amount.multiply(exchangeRate), "USD");
		};
	}

	private static List<Interest> getInterests(Random random) {
		List<Interest> interests = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			BigDecimal amount = BigDecimal.valueOf(-1000 + random.nextInt(2000));
			Interest interest = createInterest("interest" + i, YearMonth.now().plusMonths(-random.nextInt(13)), amount);
			interests.add(interest);
		}
		return interests;
	}

	public static Interest createInterest(String name, YearMonth yearMonth, BigDecimal amount) {
		return amount.compareTo(BigDecimal.ZERO) < 0 ?
				new Liability(name, new MoneyAmount(amount.abs(), "CZK"), ValidityInterval.fromYearMonth(yearMonth)) :
				new Asset(name, new MoneyAmount(amount.add(BigDecimal.ONE), "CZK"), ValidityInterval.fromYearMonth(yearMonth));
	}
}
