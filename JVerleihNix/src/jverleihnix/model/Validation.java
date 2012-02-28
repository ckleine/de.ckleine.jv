package jverleihnix.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.lang.Integer;

public class Validation {
	/**
	 * Validates simpleDateFormatStrings
	 * 
	 * @param simpleDateFormatString
	 *            	Format of the date.
	 * @param dueDateString to validate
	 * @param length : expected length of the dueDateString
	 * @param specialChar to recognize the right DateFormat
	 * @param specialCharPos : position of the specialChar
	 * 		
	 * @return True if Validation was successful else False
	 */
	public static Boolean dueDateValidation (String simpleDateFormatString, String dueDateString, int length, char specialChar, int specialCharPos){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(simpleDateFormatString);
		Date actuallDate = new Date();
		if (dueDateString.length() == length && dueDateString.charAt(specialCharPos-1) == specialChar){
			try {
				Date dueDate = simpleDateFormat.parse(dueDateString);
				final int MAX_DAY_OF_MONTH = getMaxDayOfMonth(getMonth(simpleDateFormatString, dueDateString),getYear(simpleDateFormatString, dueDateString));
				//check future date
				if (dueDate.before(actuallDate)){
					return false;
				//check month
				}else if (getMonth(simpleDateFormatString, dueDateString) > 12 || getMonth(simpleDateFormatString, dueDateString) < 1){
					return false;
				//check day
				}else if (getDay(simpleDateFormatString, dueDateString) > MAX_DAY_OF_MONTH || getDay(simpleDateFormatString, dueDateString) < 1){
					return false;
				//check time for germanDateFormat
				}else if (length == 17){
					if (getHour(simpleDateFormatString, dueDateString) > 23 || getDay(simpleDateFormatString, dueDateString) < 0){
						return false;
					} else if (getMin(simpleDateFormatString, dueDateString) > 59 || getDay(simpleDateFormatString, dueDateString) < 0){
						return false;
					}
				//check time for englishDateFormat
				}else if (length == 20){
					if (getHour(simpleDateFormatString, dueDateString) > 12 || getDay(simpleDateFormatString, dueDateString) < 1){
						return false;
					} else if (getMin(simpleDateFormatString, dueDateString) > 59 || getDay(simpleDateFormatString, dueDateString) < 0){
						return false;
					}
				}
			} catch (ParseException e) {
				return false;				
			}
			return true;
		}
		return false;
	}
	
	
	private static int getMonth(String dateFormatString, String dueDate){
		String month = "" 
				+ dueDate.charAt(dateFormatString.indexOf('M')) 
				+ dueDate.charAt(dateFormatString.indexOf('M')+1);
		return Integer.parseInt(month);
	}
	
	
	private static int getDay(String dateFormatString, String dueDate){
		String day = ""
				+ dueDate.charAt(dateFormatString.indexOf('d'))
				+ dueDate.charAt(dateFormatString.indexOf('d')+1);
		return Integer.parseInt(day);
	}
	
	
	private static int getYear(String dateFormatString, String dueDate){
		String year = ""
				+ dueDate.charAt(dateFormatString.indexOf('y'))
				+ dueDate.charAt(dateFormatString.indexOf('y')+1)
				+ dueDate.charAt(dateFormatString.indexOf('y')+2)
				+ dueDate.charAt(dateFormatString.indexOf('y')+3);
		return Integer.parseInt(year);
	}
	
	private static int getMin(String dateFormatString, String dueDate){
		String min = ""
				+ dueDate.charAt(dateFormatString.indexOf('m'))
				+ dueDate.charAt(dateFormatString.indexOf('m')+1);
		return Integer.parseInt(min);
	}
	
	private static int getHour(String dateFormatString, String dueDate){
		String hour = ""
				+ dueDate.charAt(dateFormatString.indexOf('h'))
				+ dueDate.charAt(dateFormatString.indexOf('h')+1);
		return Integer.parseInt(hour);
	}
	
	
	private static int getMaxDayOfMonth(int month, int year){
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		return cal.getActualMaximum(Calendar.DATE);
	}
}
