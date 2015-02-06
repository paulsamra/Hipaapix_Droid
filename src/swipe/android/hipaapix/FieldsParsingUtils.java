package swipe.android.hipaapix;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FieldsParsingUtils {
	public static long getTime(String dateString, String timeString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String full_time_string;
		try {
			full_time_string = convertDisplayStringToProper(dateString,
					timeString);

			Date date = sdf.parse(full_time_string);

			long timeInMinutesSinceEpoch = date.getTime() / (1000);
			return timeInMinutesSinceEpoch;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return -1;
	}

	public static long getTime(String dateString) {
		return getTime(dateString,"00:00");
	}

	public static String parsePrice(String price) {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		try {

			Number number = format.parse(price);

			return number.toString();
		} catch (Exception e) {

		}
		return "0";
	}

	public static String parseSwitchOnlineOffline(boolean b) {

		String personOnlineText = "";
		if (b) {
			personOnlineText = "online";
		} else {
			personOnlineText = "onground";
		}
		return personOnlineText;
	}

	public static String parseSwitchPrivatePublic(boolean b) {

		String personOnlineText = "";
		if (b) {
			personOnlineText = "public";
		} else {
			personOnlineText = "private";
		}
		return personOnlineText;
	}

	public static String convertDisplayStringToProper(String dayTime,
			String time) throws Exception {
		String monthName = dayTime.substring(0, 3);

		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH)
				.parse(monthName);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month_i = cal.get(Calendar.MONTH) + 1;

		String day = dayTime.substring(dayTime.indexOf(" ") + 1,
				dayTime.indexOf(","));
		int day_i = Integer.valueOf(day);

		String year = dayTime.substring(dayTime.lastIndexOf(" ") + 1,
				dayTime.length());
		int year_i = Integer.valueOf(year);

		String finalFormat = year_i + "-" + month_i + "-" + day_i;
		return finalFormat + " " + time;
	}

}