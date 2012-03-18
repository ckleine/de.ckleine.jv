package jverleihnix.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Integer;

import jverleihnix.ui.MediaType;

public class Validation {

	private static String[] dateFormatArray = { "dd.MM.yyyy, hh:mm",
			"dd.MM.yyyy", "yyyy~MM~dd, hh:mm", "yyyy~MM~dd",
			"yyyy-MM-dd, hh:mm", "yyyy-MM-dd", "MM/dd/yyyy, hh:mm a",
			"MM/dd/yyyy" };

	/**
	 * Validates dateFormatStrings
	 * 
	 * @param dueDateString
	 *            to validate
	 * 
	 * @return True if Validation was successful else False
	 */
	public static Boolean dueDateValidation(String dueDateString) {
		Date date = getDate(dueDateString);
		if (date == null) {
			return false;
		}
		String dateFormatString = getDateFormat(dueDateString);

		Date actuallDate = new Date();

		final int MAX_DAY_OF_MONTH = getMaxDayOfMonth(
				getMonth(dateFormatString, dueDateString),
				getYear(dateFormatString, dueDateString));

		// check future date
		if (date.before(actuallDate)) {
			return false;

			// check month
		} else if (getMonth(dateFormatString, dueDateString) > 12
				|| getMonth(dateFormatString, dueDateString) < 1) {
			return false;
			// check day
		} else if (getDay(dateFormatString, dueDateString) > MAX_DAY_OF_MONTH
				|| getDay(dateFormatString, dueDateString) < 1) {
			return false;

			// check time for germanDateFormat
		} else if (dateFormatString.contains("h")) {
			if (getHour(dateFormatString, dueDateString) > 23
					|| getDay(dateFormatString, dueDateString) < 0) {
				return false;
			} else if (getMin(dateFormatString, dueDateString) > 59
					|| getDay(dateFormatString, dueDateString) < 0) {
				return false;
			}
			// check time for englishDateFormat
		} else if (dateFormatString.contains("a")) {
			if (getHour(dateFormatString, dueDateString) > 12
					|| getDay(dateFormatString, dueDateString) < 1) {
				return false;
			} else if (getMin(dateFormatString, dueDateString) > 59
					|| getDay(dateFormatString, dueDateString) < 0) {
				return false;
			}
		}

		return true;

	}
	/**
	 * @param dateFormatString Format of the date string
	 * @param dueDate Date String
	 * @return Month of the date
	 * @throws NumberFormatException if substring (month) parse to integer fails 
	 */
	private static int getMonth(String dateFormatString, String dueDate)
			throws NumberFormatException {
		String month = "" + dueDate.charAt(dateFormatString.indexOf('M'))
				+ dueDate.charAt(dateFormatString.indexOf('M') + 1);
		return Integer.parseInt(month);
	}
	
	/**
	 * @param dateFormatString Format of the date string
	 * @param dueDate Date String
	 * @return Day of the date
	 * @throws NumberFormatException if substring (day) parse to integer fails 
	 */
	private static int getDay(String dateFormatString, String dueDate)
			throws NumberFormatException {
		String day = "" + dueDate.charAt(dateFormatString.indexOf('d'))
				+ dueDate.charAt(dateFormatString.indexOf('d') + 1);
		return Integer.parseInt(day);
	}
	
	/**
	 * @param dateFormatString Format of the date string
	 * @param dueDate Date String
	 * @return Year of the date
	 * @throws NumberFormatException if substring (year) parse to integer fails 
	 */
	private static int getYear(String dateFormatString, String dueDate)
			throws NumberFormatException {
		String year = "" + dueDate.charAt(dateFormatString.indexOf('y'))
				+ dueDate.charAt(dateFormatString.indexOf('y') + 1)
				+ dueDate.charAt(dateFormatString.indexOf('y') + 2)
				+ dueDate.charAt(dateFormatString.indexOf('y') + 3);
		return Integer.parseInt(year);
	}
	
	/**
	 * @param dateFormatString Format of the date string
	 * @param dueDate Date String
	 * @return Minutes of the date
	 * @throws NumberFormatException if substring (minutes) parse to integer fails 
	 */
	private static int getMin(String dateFormatString, String dueDate)
			throws NumberFormatException {
		String min = "" + dueDate.charAt(dateFormatString.indexOf('m'))
				+ dueDate.charAt(dateFormatString.indexOf('m') + 1);
		return Integer.parseInt(min);
	}
	
	/**
	 * @param dateFormatString Format of the date string
	 * @param dueDate Date String
	 * @return Hour of the date
	 * @throws NumberFormatException if substring (hour) parse to integer fails 
	 */
	private static int getHour(String dateFormatString, String dueDate)
			throws NumberFormatException {
		String hour = "" + dueDate.charAt(dateFormatString.indexOf('h'))
				+ dueDate.charAt(dateFormatString.indexOf('h') + 1);
		return Integer.parseInt(hour);
	}
	
	/**
	 * @param month of the date
	 * @param year of the date
	 * @return the last day of the specific month
	 */
	private static int getMaxDayOfMonth(int month, int year) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * Converts DateString to Date
	 * 
	 * @param dateString
	 *            to convert to Date
	 * @return Date if dateFormat exists else return null
	 */
	public static Date getDate(String dateString) {
		Date date = null;
		for (String dateFormatString : dateFormatArray) {
			if (valDate(dateString, dateFormatString)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						dateFormatString);
				dateFormat.setLenient(false);
				try {
					/* Catch wrong dateStrings like 11.1111.11 (dd.MM.yyyy)
					getYear(dateFormatString, dateString);
					getMonth(dateFormatString, dateString);
					getDay(dateFormatString, dateString);
					if (dateFormatString.contains("h")) {
						getHour(dateFormatString, dateString);
						getMin(dateFormatString, dateString);
					}*/

					date = dateFormat.parse(dateString);
				} catch (ParseException exc) {
					return date;
				} catch (NumberFormatException exc) {
					return date;
				}

			}
		}
		return date;
	}
	
	/**
	 *  Gives the date Format of an date String
	 * 
	 * @param dateString Date String to get the date format
	 * @return the date Format of the date String or null if there is no correct date Format
	 */
	public static String getDateFormat(String dateString) {
		String dateFormat = null;
		for (String dateFormatString : dateFormatArray) {
			if (valDate(dateString, dateFormatString)) {
				dateFormat = dateFormatString;
			}
		}
		return dateFormat;
	}

	
	private static boolean valDate(String dateString, String dateFormatString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
		try {
			int length = dateFormatString.length();
			if (dateFormatString.contains("a")) {
				length = length + 1;
			}

			if (dateString.length() != length) {
				return false;
			}
			@SuppressWarnings("unused")
			Date date = dateFormat.parse(dateString);
			return true;

		} catch (ParseException e) {
			return false;
		}

	}

	public static boolean valMediaType(String mediaType) {
		try {
			MediaType.valueOf(mediaType.toUpperCase());
		} catch (IllegalArgumentException exception) {
			return false;
		}
		return true;
	}
}
