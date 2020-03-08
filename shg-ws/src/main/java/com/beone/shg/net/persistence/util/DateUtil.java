package com.beone.shg.net.persistence.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;

public class DateUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public final static long HOUR = 1000l * 60l * 60l;
	public final static long DAY = HOUR * 24l;
	public final static long TIME_ZONE_ADJ =  1000l * 60l * 330l;
	public final static Date INVALID_DATE = new Date(DataUtil.LONG_TIME_01_01_2000);

	public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static DateFormat mySQL_df = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat display_df = new SimpleDateFormat("dd-MM-yyyy");
	public static DateFormat display2_df = new SimpleDateFormat("dd MMMM yyyy");
	public static DateFormat displaySMS_df = new SimpleDateFormat("dd-MMMM");

	static {
		mySQL_df.setTimeZone(TimeZone.getTimeZone("IST"));
		display_df.setTimeZone(TimeZone.getTimeZone("IST"));
	}
	
	public static Date getCurrentTimeDate() {
		return new Date(System.currentTimeMillis());
	}
	
	public static long getCurrentDayStartTime() {
		return ((long)(System.currentTimeMillis() / DAY) * DAY );
	}
	
	public static long getNoOfDaysDifference(long start, long end) {
		return ((end - start) / DAY );
	}
	
	public static long getBeforeDaysTime(int days) {
		return (new Date(System.currentTimeMillis()).getTime() - DAY * days);
	}

	public static String getCurrentDBDateStr() {
		return mySQL_df.format(new Date(System.currentTimeMillis()));
	}

	public static String getCurrentDBDateStr(int backDays) {
		return mySQL_df.format(new Date(System.currentTimeMillis() - (DAY * backDays)));
	}

	public static String getDBDateStr(Date date) {
		return mySQL_df.format(date);
	}

	public static String getDisplayDateStr(Date date) {
		return display_df.format(date);
	}

	public static String getDisplayDateStr(long time) {
		return display_df.format(new Date(time));
	}

	public static String getDisplaySMSDateStr(Date date) {
		return displaySMS_df.format(date);
	}

	public static String getDisplaySMSDateStr(long time) {
		return displaySMS_df.format(new Date(time));
	}

	public static String getCurrentDisplayDateStr() {
		return display_df.format(new Date(System.currentTimeMillis()));
	}

	public static String getCurrentDisplay2DateStr() {
		return display2_df.format(new Date(System.currentTimeMillis()));
	}

	public static String getCurrentDisplaySMSDateStr() {
		return displaySMS_df.format(new Date(System.currentTimeMillis()));
	}

	public static String getInputDateStr(Date date) {
		return df.format(date);
	}

	public static String getInputDateStr(long time) {
		return df.format(new Date(time));
	}

	public static String getCurrentInputDateStr() {
		return df.format(new Date(System.currentTimeMillis()));
	}

	public static int getDaysInCurrentMonth() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static String convertTimeToDisplayString(long seconds) {

		int day = (int) TimeUnit.SECONDS.toDays(seconds);
	    long hours = TimeUnit.SECONDS.toHours(seconds) -
	                 TimeUnit.DAYS.toHours(day);
	    long minute = TimeUnit.SECONDS.toMinutes(seconds) -
	                  TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds));
	    
	    String timeStr = DataUtil.EMPTY_STRING;
	    if(day > 0) {
	    	timeStr += day + " Day : ";
	    } 
	    if(hours > 0 || timeStr != DataUtil.EMPTY_STRING) {
	    	timeStr += hours + " Hour : ";
	    } 
	    if(minute > 0 || timeStr != DataUtil.EMPTY_STRING) {
	    	timeStr += minute + " Min";
	    } 
	    
	    return timeStr;
	}
	
	public static String convertTimeToDisplayStringSec(long seconds) {
	    int day = (int) TimeUnit.SECONDS.toDays(seconds);
	    long hours = TimeUnit.SECONDS.toHours(seconds) -
	                 TimeUnit.DAYS.toHours(day);
	    long minute = TimeUnit.SECONDS.toMinutes(seconds) -
	                  TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds));
	    long second = TimeUnit.SECONDS.toSeconds(seconds) -
	                  TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));
	    
	    String timeStr = DataUtil.EMPTY_STRING;
	    if(day > 0) {
	    	timeStr += day + " Day : ";
	    } 
	    if(hours > 0 || timeStr != DataUtil.EMPTY_STRING) {
	    	timeStr += hours + " Hour : ";
	    } 
	    if(minute > 0 || timeStr != DataUtil.EMPTY_STRING) {
	    	timeStr += minute + " Min : ";
	    } 
	    if(second > 0 || timeStr != DataUtil.EMPTY_STRING) {
	    	timeStr += second + " Sec";
	    } 
	    
	    return timeStr;
	}
	
	public static Date convertStringToDate(String dateString) {
		if(dateString != null && !dateString.isEmpty()) {
			try {
				return df.parse(dateString);
			} catch (ParseException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return null;
	}

	public static Date convertStringToDateWithCurrentDefault(String dateString){
		if(dateString != null && !dateString.isEmpty()) {
			try {
				return df.parse(dateString);
			} catch (ParseException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return new Date(System.currentTimeMillis());
	}

	public static long convertStringToLong(String dateString) {
		if(dateString != null && !dateString.isEmpty()) {
			try {
				Date date = df.parse(dateString);
				if(date != null) {
					return date.getTime();
				}
			} catch (ParseException e) {
				LOGGER.error(e.getLocalizedMessage());
			}
		}
		return DataUtil.ZERO_LONG;
	}

	public static String convertTimeToString(long time){
		if(time > DataUtil.ZERO_LONG) {
			Date date = new Date(time);
			return df.format(date).toString();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static String convertDateToString(Date date){
		if(date != null) {
			return df.format(date).toString();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static Date convertTimeToDate(long time){
		if(time > DataUtil.ZERO_LONG) {
			return new Date(time);
		}
		return null;
	}

	public static long convertDateToTime(Date date){
		if(date != null) {
			return date.getTime();
		}
		return 0L;
	}

	public static Date convertTimeToDateWithCurrentDefault(long time){
		if(time > DataUtil.ZERO_LONG) {
			return new Date(time);
		}
		return new Date(System.currentTimeMillis());
	}

	public static Date getExpectedDate(long time, int noOfMonths){
		
		if(time > DataUtil.ZERO_LONG) {
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTimeInMillis(time);
			calendar.add(Calendar.MONTH, noOfMonths);
			
			int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

			if(dayOfMonth > 15) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			return calendar.getTime();
		}
		return null;
	}

	public static Date getDateAfterMonths(Date date, int noOfMonths){
		
		if(date != null) {
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTimeInMillis(date.getTime());
			calendar.add(Calendar.MONTH, noOfMonths);
			
			return calendar.getTime();
		}
		return null;
	}

	public static Date getDayOfMonth(long time, int dayOfMonth){
		
		if(dayOfMonth > DataUtil.ZERO_LONG) {
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTimeInMillis(time);		
			calendar.set(Calendar.DAY_OF_MONTH, (dayOfMonth - 1));
			
			return calendar.getTime();
		}
		return null;
	}

	public static int getDayOfMonth(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMonth(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		return calendar.get(Calendar.MONTH);
	}

	public static String getMonthName(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		int month = calendar.get(Calendar.MONTH);
		return getMonthName(month);
	}

	public static String getMonthShort(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		int month = calendar.get(Calendar.MONTH);
		return getMonthShortName(month);
	}

	public static String getMonthYearShort(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		int month = calendar.get(Calendar.MONTH);
		return getMonthShortName(month) + " - " + calendar.get(Calendar.YEAR);
	}

	public static Date parseMonthYearShort(String monthYear){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();

		if(monthYear.equals(EnumConst.AsOnDate)) {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		else {
			String[] strs = monthYear.split(" - ");
			calendar.set(Calendar.YEAR, Integer.parseInt(strs[1]));
			calendar.set(Calendar.MONTH, getMonthShortIndex(strs[0]));
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);
		}

		return calendar.getTime();
	}

	public static boolean checkSameMonthTime(long t1, long t2) {

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(t1);		
		int m1 = calendar.get(Calendar.MONTH);
		int y1 = calendar.get(Calendar.YEAR);

		calendar.clear();
		calendar.setTimeInMillis(t2);		
		int m2 = calendar.get(Calendar.MONTH);
		int y2 = calendar.get(Calendar.YEAR);

		return ((m1 == m2) && (y1 == y2));
	}

	public static int getYear(long time){

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTimeInMillis(time);		
		return calendar.get(Calendar.YEAR);
	}

	public static String getMonthName(int month){
		
		switch(month) {
		case Calendar.JANUARY:
			return "JANUARY";
		case Calendar.FEBRUARY:
			return "FEBRUARY";
		case Calendar.MARCH:
			return "MARCH";
		case Calendar.APRIL:
			return "APRIL";
		case Calendar.MAY:
			return "MAY";
		case Calendar.JUNE:
			return "JUNE";
		case Calendar.JULY:
			return "JULY";
		case Calendar.AUGUST:
			return "AUGUST";
		case Calendar.SEPTEMBER:
			return "SEPTEMBER";
		case Calendar.OCTOBER:
			return "OCTOBER";
		case Calendar.NOVEMBER:
			return "NOVEMBER";
		case Calendar.DECEMBER:
			return "DECEMBER";			
		}
		return null;
	}

	public static int getMonthIndex(String month){
		
		switch(month) {
		case "JANUARY":
			return Calendar.JANUARY;
		case "FEBRUARY":
			return Calendar.FEBRUARY;
		case "MARCH":
			return Calendar.MARCH;
		case "APRIL":
			return Calendar.APRIL;
		case "MAY":
			return Calendar.MAY;
		case "JUNE":
			return Calendar.JUNE;
		case "JULY":
			return Calendar.JULY;
		case "AUGUST":
			return Calendar.AUGUST;
		case "SEPTEMBER":
			return Calendar.SEPTEMBER;
		case "OCTOBER":
			return Calendar.OCTOBER;
		case "NOVEMBER":
			return Calendar.NOVEMBER;
		case "DECEMBER":
			return Calendar.DECEMBER;			
		}
		return -1;
	}

	public static String getMonthShortName(int month){
		
		switch(month) {
		case Calendar.JANUARY:
			return "JAN";
		case Calendar.FEBRUARY:
			return "FEB";
		case Calendar.MARCH:
			return "MAR";
		case Calendar.APRIL:
			return "APR";
		case Calendar.MAY:
			return "MAY";
		case Calendar.JUNE:
			return "JUN";
		case Calendar.JULY:
			return "JUL";
		case Calendar.AUGUST:
			return "AUG";
		case Calendar.SEPTEMBER:
			return "SEP";
		case Calendar.OCTOBER:
			return "OCT";
		case Calendar.NOVEMBER:
			return "NOV";
		case Calendar.DECEMBER:
			return "DEC";			
		}
		return null;
	}

	public static int getMonthShortIndex(String month){
		
		switch(month) {
		case "JAN":
			return Calendar.JANUARY;
		case "FEB":
			return Calendar.FEBRUARY;
		case "MAR":
			return Calendar.MARCH;
		case "APR":
			return Calendar.APRIL;
		case "MAY":
			return Calendar.MAY;
		case "JUN":
			return Calendar.JUNE;
		case "JUL":
			return Calendar.JULY;
		case "AUG":
			return Calendar.AUGUST;
		case "SEP":
			return Calendar.SEPTEMBER;
		case "OCT":
			return Calendar.OCTOBER;
		case "NOV":
			return Calendar.NOVEMBER;
		case "DEC":
			return Calendar.DECEMBER;			
		}
		return -1;
	}
	
	public static int getDayOfMonth(Date date) {
		if(date != null && date != DataUtil.INVALID_DATE) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_MONTH);
		}
		
		return -1;
	}
	
	public static String toString(Date date) {
		if(date != null && date != DataUtil.INVALID_DATE) {
			return df.format(date);
		}
		return "";
	}

	public static long getStartTimeOfMonth(String dateString){

		long time = convertStringToLong(dateString);

		if(time > DataUtil.ZERO_LONG) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTimeInMillis(time);
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			return calendar.getTime().getTime();
		}			
		return DataUtil.ZERO_LONG;
	}

	public static long getEndTimeOfMonth(String dateString) {

		long time = convertStringToLong(dateString);
		return getEndTimeOfMonth(time);
	}

	public static long getStartTimeOfCurMonth(){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime().getTime();
	}

	public static long getEndTimeOfCurMonth() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime().getTime();
	}

	public static long getEndTimeOfMonth(long time) {

		if(time > DataUtil.ZERO_LONG) {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			calendar.clear();
			calendar.setTimeInMillis(time);
			
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, 1);

			// Return Time One Hour before the End of Month
			return calendar.getTime().getTime();
		}			
		return DataUtil.ZERO_LONG;
	}

	public static int getNoOfDaysFromDueDate(Date date, int dueDay){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		if(dueDay >= dayOfMonth) {
			return dueDay - dayOfMonth;
		} else {
			return 30 + dueDay - dayOfMonth;
		}
	}

	public static Date getDueDate(Date date, int dueDay){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		if(dueDay < dayOfMonth) {
			calendar.add(Calendar.MONTH, 1);
		}
		
		calendar.set(Calendar.DAY_OF_MONTH, dueDay);
		return calendar.getTime();
	}

	public static Date getDueDate(Date date, int dueDay, int dueInMonth){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		
		if(dueDay < dayOfMonth) {
			calendar.add(Calendar.MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, dueDay);
		calendar.add(Calendar.MONTH, dueInMonth);
		
		return calendar.getTime();
	}

	public static int getNoOfMonthsPassed(Date date){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		
		int curMonths = calendar.get(Calendar.MONTH) + (calendar.get(Calendar.YEAR) * 12);

		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		
		int months = calendar.get(Calendar.MONTH) + (calendar.get(Calendar.YEAR) * 12);
		
		return (curMonths - months - 2);
	}

	public static Date getNextMonthDate(Date date){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(date.getTime());
		calendar.add(Calendar.MONTH, 1);
		
		return calendar.getTime();
	}

	public static boolean isCurrentMonth(long time){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(time);		
		int month = calendar.get(Calendar.MONTH);
		calendar.clear();
		calendar.setTimeInMillis(System.currentTimeMillis());		
		int curMonth = calendar.get(Calendar.MONTH);
		return (month == curMonth);
	}

	public static Date getEndOfMonthLastMonth() {

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return calendar.getTime();
	}

	public static long getEndOfMonthTime(long time){

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.clear();
		calendar.setTimeInMillis(time);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, days);
		
		return calendar.getTime().getTime();
	}

	public static int getDiffInMonths(Date startDate, Date endDate){
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(startDate);
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		
		return diffMonth;
	}
}
