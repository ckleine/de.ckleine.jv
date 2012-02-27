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
package jverleihnix.ui;

import javax.swing.AbstractListModel;

import jverleihnix.app.Application;

/**
 * The UI's model that backs the list with the rental entries. All requests are
 * routed directly to the @see {@link Application} facade.
 */
@SuppressWarnings("serial")
public class RentalListModel extends AbstractListModel {

	/**
	 * Adds a new entry to this model
	 * 
	 * @param entry
	 *            to add
	 */
	public void addElement(IUIRentalEntry entry) {
		Application.instance.addEntry(entry);
		fireContentsChanged(this, 0, Application.instance.getNumberOfRentals());
	}

	/**
	 * @see AbstractListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return Application.instance.getEntry(index);
	}

	/**
	 * @see AbstractListModel#getSize()
	 */
	@Override
	public int getSize() {
		return Application.instance.getNumberOfRentals();
	}

	/**
	 * Modifies the entry stored at index in the model by overwritting it with
	 * the values of the given entry.
	 * 
	 * @param index
	 *            of the entry to modify
	 * @param entry
	 *            with new values
	 */
	public void modifyEntry(int index, IUIRentalEntry entry) {
		Application.instance.modifyEntry(entry, index);
		fireContentsChanged(this, 0, Application.instance.getNumberOfRentals());
	}

	/**
	 * Removes an entry from the model.
	 * 
	 * @param index
	 *            of entry to remove.
	 */
	public void finishEntry(int index) {
		Application.instance.finishEntry(index);
		fireContentsChanged(this, 0, Application.instance.getNumberOfRentals());
	}

}
