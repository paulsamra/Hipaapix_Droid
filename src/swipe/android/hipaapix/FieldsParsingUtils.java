package swipe.android.hipaapix;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class FieldsParsingUtils {
	public static long getTime(String dateString, String timeString) {
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(dateString);
			long timeInMillisSinceEpoch = date.getTime(); 
			long timeInSeconds = timeInMillisSinceEpoch / ( 1000);
			return timeInSeconds;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
		if(!time.equals("")){
			
		return finalFormat + " " + time;
		}else{
			return finalFormat;
		}
	}

	public static String convertDisplayStringToGanz(long s) {
Date date = new Date (1000L*s);
SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
String formattedDate = sdf.format(date);
return formattedDate;
	}
}