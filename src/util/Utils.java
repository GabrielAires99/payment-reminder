package util;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static boolean isToday(Date date) {
		return resetHour(date).getTime() == resetHour(new Date()).getTime();
	}

	public static boolean isNext(Date date, int daysGap) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, daysGap);
		return resetHour(cal.getTime()).getTime() == resetHour(date).getTime();
	}

	private static Date resetHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
