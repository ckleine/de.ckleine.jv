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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import jverleihnix.io.RentalEntryIO;
import jverleihnix.model.Model;
import jverleihnix.ui.DefaultUIRentalEntry;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.MediaType;

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
	 * @return True if loading was successful otherwise false
	 * @throws ApplicationException
	 *             if loading the file fails.
	 */
	public boolean load(String fileName) throws ApplicationException {
		return RentalEntryIO.load(fileName);
	}

	/**
	 * Stores the rental entries in the file with the given name.
	 * 
	 * @param fileName
	 *            of the file to write the rental entries to.
	 * @return True if storing the entries was successful otherwise false
	 * @throws ApplicationException
	 *             if storing fails
	 */
	@SuppressWarnings("finally")
	public boolean store(String fileName) throws ApplicationException {
		return RentalEntryIO.save(fileName);
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
		System.out.println("validateEntry: NIY");
		return new String[0];
	}

}
