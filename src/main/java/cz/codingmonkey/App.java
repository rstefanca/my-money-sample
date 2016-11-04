package cz.codingmonkey;

import cz.codingmonkey.domain.*;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

		last12MonthsStream.map(InterestSummary::forYearMonth).map(interestSummary -> {
			interests.forEach(interestSummary::addInterestMatchingYearMonth);
			return interestSummary;
		}).forEach(System.out::println);
	}

	private static List<Interest> getInterests(Random random) {
		List<Interest> interests = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			BigDecimal amount = BigDecimal.valueOf(-1000 + random.nextInt(2000));

			Interest interest = interestFactory("interest" + i, YearMonth.now().plusMonths(-random.nextInt(13)), amount);
			interests.add(interest);
		}
		return interests;
	}

	public static Interest interestFactory(String name, YearMonth yearMonth, BigDecimal amount) {
		return amount.compareTo(BigDecimal.ZERO) < 0 ?
				new Liability(name, amount.abs(), ValidityInterval.fromYearMonth(yearMonth)) :
				new Asset(name, amount.add(BigDecimal.ONE), ValidityInterval.fromYearMonth(yearMonth));

	}
}
