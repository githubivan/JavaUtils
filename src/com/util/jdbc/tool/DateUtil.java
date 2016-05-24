package com.util.jdbc.tool;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	 public static int getWeekNo() {
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(new Date());
		 return cal.get(Calendar.DAY_OF_WEEK)-1;
	 }
}
