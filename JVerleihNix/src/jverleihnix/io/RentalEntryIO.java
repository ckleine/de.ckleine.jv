package jverleihnix.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import jverleihnix.app.Application;
import jverleihnix.app.ApplicationException;
import jverleihnix.internationalisation.Messages;
import jverleihnix.model.Model;
import jverleihnix.model.Validation;
import jverleihnix.ui.DefaultUIRentalEntry;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.MediaType;

public class RentalEntryIO {

	/**
	 * Save the rental entries in the file with the given name.
	 * 
	 * @param fileName
	 *            of the file to write the rental entries to.
	 * @return True if storing the entries was successful
	 * @throws ApplicationException
	 *             if storing fails
	 * @throws IOException
	 *             if storing fails
	 */
	public static void save(String fileName) throws ApplicationException,
			IOException {
		Writer writer = new FileWriter(fileName);
		String locale = Messages.getLocale().toString().substring(0, 2);
		for (IUIRentalEntry rental : Model.instance.getEntries()) {
			String buffer = locale + "\t" + rental.getDueDate() + "\t"
					+ rental.getDescription() + "\t"
					+ rental.getMediaType().toString() + "\n";
			writer.write(buffer);
		}
		writer.close();
	}

	/**
	 * Loads rental entries from the file that is specified by the file name
	 * given.
	 * 
	 * @param fileName
	 *            To load rental entries from
	 * @return True if loading was successful
	 * @throws ApplicationException
	 *             if loading the file fails.
	 * @throws IOException
	 *             if loading the file fails
	 */
	public static String load(String fileName) throws ApplicationException,
			IOException {
		File file = new File(fileName);
		LineNumberReader line = new LineNumberReader(new FileReader(file));
		String rentalString;
		String errorLog = "";
		while ((rentalString = line.readLine()) != null) {
			String[] rentalArray = rentalString.split("\t");
			// only correct selected with correct dates selected
			if (rentalArray.length == 4
					&& Validation.getDate(rentalArray[1]) != null
					&& Validation.valMediaType(rentalArray[3])) {
				MediaType mediaType = MediaType.valueOf(rentalArray[3]
						.toUpperCase());
				IUIRentalEntry entry = new DefaultUIRentalEntry(rentalArray[1],
						rentalArray[2], mediaType);
				Application.instance.addEntry(entry);
			} else {
				if (Validation.getDate(rentalArray[1]) == null) {
					errorLog = errorLog + Messages.getString("uncorrectDate")
							+ line.getLineNumber() + "<br>";
				}

				if (!Validation.valMediaType(rentalArray[3])) {
					errorLog = errorLog
							+ Messages.getString("uncorrectMediaType")
							+ line.getLineNumber() + "<br>";
				}
				if (!(rentalArray.length == 4)) {
					errorLog = errorLog + Messages.getString("unknownError")
							+ line.getLineNumber() + "<br>";
				}
			}
		}
		return errorLog;
	}
}
