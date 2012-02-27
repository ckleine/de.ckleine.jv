package jverleihnix.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jverleihnix.app.Application;
import jverleihnix.app.ApplicationException;
import jverleihnix.model.Model;
import jverleihnix.ui.DefaultUIRentalEntry;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.MediaType;

public class RentalEntryIO {
	
	
	@SuppressWarnings("finally")
	public static boolean save(String fileName){
		
		Writer writer = null;
		try{
			writer = new FileWriter(fileName);
			for (IUIRentalEntry rental : Model.instance.getEntries()){
				String buffer = "de" + "\t" + rental.getDueDate() + "\t" + rental.getDescription() + "\t" + rental.getMediaType().toString()+ "\n";
				writer.write(buffer);
			}
			
		} catch(IOException exf) {
			throw new ApplicationException("store: NIY");
		}finally{
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					return true;
				}
			}
			
			return false;
		}
	}
	
	public static boolean load(String fileName) throws ApplicationException{
		File file = new File(fileName);
		try {
		      BufferedReader line = new BufferedReader(new FileReader (file));
		      String rentalString;
		      while ((rentalString = line.readLine()) != null){
		    	  String[] rentalArray = rentalString.split("\t");
		    	  //correct line selection 
			      if (rentalArray.length == 4){
			    	  MediaType mediaType = MediaType.valueOf(rentalArray[3]);
			    	  IUIRentalEntry entry = new DefaultUIRentalEntry(rentalArray[1],rentalArray[2], mediaType);
			    	  Application.instance.addEntry(entry);
			      }
		      	}
		      return true;
		      
		    } catch(IOException ex ) {
		    	throw new ApplicationException("load: NIY");
		    }
	}
}
