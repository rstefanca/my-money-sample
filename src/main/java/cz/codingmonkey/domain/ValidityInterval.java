package cz.codingmonkey.domain;

import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.util.Date;

/**
 * @author rstefanca
 */
@Value
@NonFinal
public class ValidityInterval {

	private LocalDate from;
	private LocalDate to;

	ValidityInterval(LocalDate from) {
		this(from, null);
	}

	ValidityInterval(@NonNull LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
		if (to != null && to.isBefore(from)) {
			throw new IllegalArgumentException("to must not be before from");
		}
	}

	public static ValidityInterval fromYearMonth(YearMonth yearMonth) {
		return new ValidityInterval(
				firstDayOfYearMonth(yearMonth),
				lastDayOfYearMonth(yearMonth)
		);
	}

	public static ValidityInterval fromDate(Date from) {
		return new ValidityInterval(new LocalDate(from));
	}

	public static ValidityInterval closed(@NonNull  Date from, @NonNull Date to) {
		return new ValidityInterval(new LocalDate(from), new LocalDate(to));
	}

	boolean isValidInYearMonth(@NonNull YearMonth yearMonth) {

		LocalDate parameterLastDay = lastDayOfYearMonth(yearMonth);
		if (parameterLastDay.isBefore(from)) {
			return false;
		}

		LocalDate parameterFirstDay = firstDayOfYearMonth(yearMonth);

		return !(to != null && parameterFirstDay.isAfter(to));
	}

	private static LocalDate firstDayOfYearMonth(YearMonth yearMonth) {
		return yearMonth.toLocalDate(1);
	}

	private static LocalDate lastDayOfYearMonth(YearMonth yearMonth) {
		return yearMonth.toLocalDate(1).dayOfMonth().withMaximumValue();
	}

	public static ValidityInterval fromDate(LocalDate now) {
		return new ValidityInterval(now);
	}
}
