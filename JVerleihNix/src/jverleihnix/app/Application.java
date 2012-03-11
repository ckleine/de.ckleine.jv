/*
 * Copyright 2012 Ivan Bogicevic, Markus Knauß, Daniel Kulesz, Holger Röder, Matthias Wetzel, Jan-Peter Ostberg, Daniel Schleicher
 * 
 * This file is part of JVerleihNix.
 * 
 * JVerleihNix is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JVerleihNix is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JVerleihNix.  If not, see <http://www.gnu.org/licenses/>.
 */
package jverleihnix.app;


import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import jverleihnix.io.RentalEntryIO;
import jverleihnix.model.Model;
import jverleihnix.model.Validation;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.JVerleihNixFrame;

/**
 * Application facade that provides access to the implementation of all the use
 * cases that are defined for the JVerleihNix application.
 */
public class Application {

	/**
	 * Singleton attriute to access an instance of this class.
	 */
	public static final Application instance = new Application();

	private Application() {
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
	 */
	public boolean load(String fileName) throws ApplicationException, IOException {
		String errorLog = RentalEntryIO.load(fileName);
		if (errorLog.length()>0){
			JVerleihNixFrame.errorMessage(errorLog);
		}
		return true;
	}

	/**
	 * Stores the rental entries in the file with the given name.
	 * 
	 * @param fileName
	 *            of the file to write the rental entries to.
	 * @return True if storing the entries was successful
	 * @throws ApplicationException or IOException
	 *             if storing fails
	 */
	public boolean store(String fileName) throws ApplicationException, IOException {
		RentalEntryIO.save(fileName);
		return true;
	}

	/**
	 * @return The number of rental entries hold in the model
	 */
	public int getNumberOfRentals() {
		return Model.instance.getEntries().size();
	}

	/**
	 * @return The rental entry that is stored on the given index in the model.
	 */
	public IUIRentalEntry getEntry(int index) {
		return Model.instance.getEntries().get(index);
	}

	/**
	 * Adds a new rental entry to the model.
	 * 
	 * @param entry
	 *            to add
	 */
	public void addEntry(IUIRentalEntry entry) {
		Model.instance.addEntry(entry);
	}

	/**
	 * Modifies the entry stored at the given index by overwritting it with the
	 * given entry.
	 * 
	 * @param entry
	 *            to write over the entry at index
	 * @param index
	 *            of the entry to modify
	 */
	public void modifyEntry(IUIRentalEntry entry, int index) {
		Model.instance.replaceEntry(entry, index);
		
	}

	/**
	 * Removes an entry from the model when it is finished.
	 * 
	 * @param index
	 *            of the entry to remove
	 */
	public void finishEntry(int index) {
		Model.instance.removeEntry(index);
	}

	/**
	 * Checks the values that are set for an entry if they are valid.
	 * 
	 * @param entry
	 *            to check for validity
	 * @return Array with messages that describe validation errors. If no errors
	 *         are found an empty array is returned.
	 */
	public String[] validateEntry(IUIRentalEntry entry) {
		List<String> errorLog = new ArrayList<String>();

		//date validation
		if (!Validation.dueDateValidation(entry.getDueDate())){
			errorLog.add(Messages.getString("Application.0")); //$NON-NLS-1$
		}
		
		//description validation
		if (entry.getDescription().isEmpty()){
			errorLog.add(Messages.getString("Application.1")); //$NON-NLS-1$
		}
		
		
		//array to String[]
		String[] errorLogString = new String[errorLog.size()];
		for (String error : errorLog){
			errorLogString[errorLog.indexOf(error)] = error;
		}

		
		return errorLogString;
	}
	

}
