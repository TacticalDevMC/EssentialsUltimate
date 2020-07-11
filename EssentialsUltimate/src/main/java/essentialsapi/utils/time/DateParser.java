package essentialsapi.utils.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translates unix timestamps / durations into a readable format
 *
 * @author khobbits, drtshock, vemacs
 * see: https://github.com/drtshock/Essentials/blob/2.x/Essentials/src/com/earth2me/essentials/utils/DateUtil.java
 */
public final class DateParser {

	private static final Pattern TIME_PATTERN = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?" + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
	private static final int MAX_YEARS = 100000;

	private DateParser() {
	}

	/**
	 * Converts a time string to a unix timestamp
	 *
	 * @param time   the time string
	 * @param future if the date is in the future, as opposed to the past
	 * @return a unix timestamp
	 * @throws IllegalArgumentException if the date input was invalid
	 */
	public static long parseDate(String time, boolean future) throws IllegalArgumentException {
		Matcher matcher = TIME_PATTERN.matcher(time);
		int years = 0, months = 0, weeks = 0, days = 0, hours = 0, minutes = 0, seconds = 0;

		boolean found = false;
		while (matcher.find()) {
			if (matcher.group() == null || matcher.group().isEmpty()) {
				continue;
			}
			for (int i = 0; i < matcher.groupCount(); i++) {
				if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
					found = true;
					break;
				}
			}
			if (found) {
				if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
					years = Integer.parseInt(matcher.group(1));
				}
				if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
					months = Integer.parseInt(matcher.group(2));
				}
				if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
					weeks = Integer.parseInt(matcher.group(3));
				}
				if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
					days = Integer.parseInt(matcher.group(4));
				}
				if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
					hours = Integer.parseInt(matcher.group(5));
				}
				if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
					minutes = Integer.parseInt(matcher.group(6));
				}
				if (matcher.group(7) != null && !matcher.group(7).isEmpty()) {
					seconds = Integer.parseInt(matcher.group(7));
				}
				break;
			}
		}

		if (!found) {
			throw new IllegalArgumentException();
		}

		Calendar c = new GregorianCalendar();
		if (years > 0) {
			if (years > MAX_YEARS) {
				years = MAX_YEARS;
			}
			c.add(Calendar.YEAR, years * (future ? 1 : -1));
		}
		if (months > 0) {
			c.add(Calendar.MONTH, months * (future ? 1 : -1));
		}
		if (weeks > 0) {
			c.add(Calendar.WEEK_OF_YEAR, weeks * (future ? 1 : -1));
		}
		if (days > 0) {
			c.add(Calendar.DAY_OF_MONTH, days * (future ? 1 : -1));
		}
		if (hours > 0) {
			c.add(Calendar.HOUR_OF_DAY, hours * (future ? 1 : -1));
		}
		if (minutes > 0) {
			c.add(Calendar.MINUTE, minutes * (future ? 1 : -1));
		}
		if (seconds > 0) {
			c.add(Calendar.SECOND, seconds * (future ? 1 : -1));
		}

		Calendar max = new GregorianCalendar();
		max.add(Calendar.YEAR, 10);

		if (c.after(max)) {
			return max.getTimeInMillis() / 1000 + 1;
		}
		return c.getTimeInMillis() / 1000 + 1;
	}

}
