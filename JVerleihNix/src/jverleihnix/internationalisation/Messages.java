package jverleihnix.internationalisation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private static Locale locale = Locale.getDefault();

	private static String BUNDLE_NAME = "jverleihnix.internationalisation.messages";

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static void setLocale(Locale newLocale) {
		locale = newLocale;
	}

	public static Locale getLocale() {
		return locale;
	}
}
