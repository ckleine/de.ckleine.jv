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

import jverleihnix.ui.IUIRentalEntry;

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
		System.out.println("load: NIY");
		throw new ApplicationException("load: NIY");
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
	public boolean store(String fileName) throws ApplicationException {
		System.out.println("store: NIY");
		throw new ApplicationException("store: NIY");
	}

	/**
	 * @return The number of rental entries hold in the model
	 */
	public int getNumberOfRentals() {
		System.out.println("getNumberOfRentals: NIY");
		return 0;
	}

	/**
	 * @return The rental entry that is stored on the given index in the model.
	 */
	public IUIRentalEntry getEntry(int index) {
		System.out.println("getEntry: NIY");
		return null;
	}

	/**
	 * Adds a new rental entry to the model.
	 * 
	 * @param entry
	 *            to add
	 */
	public void addEntry(IUIRentalEntry entry) {
		System.out.println("addEntry: NIY");
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
		System.out.println("modifyEntry: NIY");
	}

	/**
	 * Removes an entry from the model when it is finished.
	 * 
	 * @param index
	 *            of the entry to remove
	 */
	public void finishEntry(int index) {
		System.out.println("finishEntry: NIY");
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
