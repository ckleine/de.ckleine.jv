package jverleihnix.io;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import jverleihnix.app.Application;
import jverleihnix.app.ApplicationException;
import jverleihnix.model.Model;
import jverleihnix.model.Validation;
import jverleihnix.ui.DefaultUIRentalEntry;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.MediaType;

public class RentalEntryIO {
	
	
	public static void save(String fileName) throws ApplicationException, IOException {
		Writer writer = new FileWriter(fileName);
		for (IUIRentalEntry rental : Model.instance.getEntries()){
			String buffer = "de" + "\t" + rental.getDueDate() + "\t" + rental.getDescription() + "\t" + rental.getMediaType().toString()+ "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			writer.write(buffer);
		}
		writer.close();
	}
	
	
	/**
	 * Loads rental entries from the file that is specified by the file name
	 * given.
	 * 
	 * @param fileName
	 * @return errorLog List
	 * @throws ApplicationException
	 * @throws IOException
	 */
	public static String load(String fileName) throws ApplicationException, IOException{
		File file = new File(fileName);
		LineNumberReader line = new LineNumberReader(new FileReader (file));
	    String rentalString;
	    String errorLog = ""; //$NON-NLS-1$
	    while ((rentalString = line.readLine()) != null){
	    	String[] rentalArray = rentalString.split("\t"); //$NON-NLS-1$
	    	//only correct selected with correct dates selected
		    if (rentalArray.length == 4 && Validation.getDate(rentalArray[1])!= null && Validation.valMediaType(rentalArray[3])){
		    	MediaType mediaType = MediaType.valueOf(rentalArray[3].toUpperCase());
		    	IUIRentalEntry entry = new DefaultUIRentalEntry(rentalArray[1],rentalArray[2], mediaType);
		    	Application.instance.addEntry(entry);
		    }
		    else{
		    	if (Validation.getDate(rentalArray[1]) == null){
		    		errorLog = errorLog + Messages.getString("RentalEntryIO.7") + line.getLineNumber() + "<br>"; //$NON-NLS-1$ //$NON-NLS-2$
		    	}
		    		
		    	if (!Validation.valMediaType(rentalArray[3])){
		    		errorLog = errorLog + Messages.getString("RentalEntryIO.9") + line.getLineNumber() + "<br>"; //$NON-NLS-1$ //$NON-NLS-2$
		    	}
		    	if (!(rentalArray.length == 4)){
		    		errorLog = errorLog + Messages.getString("RentalEntryIO.11") + line.getLineNumber() + "<br>"; //$NON-NLS-1$ //$NON-NLS-2$
		    	}
		    }
	    } 
	    return errorLog;
	}
}
