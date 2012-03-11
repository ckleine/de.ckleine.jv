package jverleihnix.internationalisation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	
	private static Locale locale = Locale.getDefault();
	
	
	private static final String BUNDLE_NAME = "jverleihnix.internationalisation.messages"; 
		
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, locale);
	
	


	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static void setLocale(Locale newLocale){
		locale = newLocale;
	}
}

