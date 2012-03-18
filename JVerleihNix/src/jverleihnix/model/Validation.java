package jverleihnix.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		Date actuallDate = new Date();

		// check future date
		if (date.before(actuallDate)) {
			return false;
		}
		return true;

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
					date = dateFormat.parse(dateString);
				} catch (ParseException exc) {
					return date;
				}
			}
		}
		return date;
	}

	/**
	 * Gives the date Format of an date String
	 * 
	 * @param dateString
	 *            Date String to get the date format
	 * @return the date Format of the date String or null if there is no correct
	 *         date Format
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
		dateFormat.setLenient(false);
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
