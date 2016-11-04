package cz.codingmonkey.domain;

import org.joda.time.YearMonth;

import java.util.Date;

import static java.util.Objects.requireNonNull;

/**
 * @author rstefanca
 */
public class ValidityInterval {

	private final Date from;
	private final Date to;

	ValidityInterval(Date from) {
		this(from, null);
	}

	ValidityInterval(Date from, Date to) {
		this.from = requireNonNull(from, "from");
		this.to = to;
		if (to != null && to.before(from)) {
			throw new IllegalArgumentException("to must not be before from");
		}
	}

	@Override
	public String toString() {
		return "ValidityInterval{" +
				"from=" + from +
				", to=" + to +
				'}';
	}

	public static ValidityInterval fromYearMonth(YearMonth yearMonth) {
		return new ValidityInterval(
				firstDayOfYearMonth(yearMonth),
				lastDayOfYearMonth(yearMonth)
		);
	}

	public static ValidityInterval fromDate(Date from) {
		return new ValidityInterval(from);
	}

	public static ValidityInterval closed(Date from, Date to) {
		return new ValidityInterval(from, requireNonNull(to, "to"));
	}

	boolean isValidInYearMonth(YearMonth yearMonth) {
		Date parameterLastDay = lastDayOfYearMonth(yearMonth);
		if (parameterLastDay.before(from)) {
			return false;
		}

		Date parameterFirstDay = firstDayOfYearMonth(yearMonth);

		return !(to != null && parameterFirstDay.after(to));
	}

	private static Date firstDayOfYearMonth(YearMonth yearMonth) {
		requireNonNull(yearMonth, "yearMonth");
		return yearMonth.toLocalDate(1).toDate();
	}

	private static Date lastDayOfYearMonth(YearMonth yearMonth) {
		requireNonNull(yearMonth, "yearMonth");
		return yearMonth.toLocalDate(1).dayOfMonth().withMaximumValue().toDate();
	}
}
