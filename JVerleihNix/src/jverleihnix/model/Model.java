package jverleihnix.model;

import java.util.ArrayList;
import java.util.List;
import jverleihnix.ui.IUIRentalEntry;

public class Model {
	private List<IUIRentalEntry> rentals;
	
	/**
	 * Singleton field to access this model.
	 */
	public final static Model instance = new Model();
	
	private Model() {
		rentals = new ArrayList<IUIRentalEntry>();

	}
	
	public List<IUIRentalEntry> getEntries() {
		return rentals;
	}
	
	public void setEntries(List<IUIRentalEntry> rentals) {
		this.rentals = rentals;
	}
	
	/**
	 * @param entry to remove from this model
	 */
	public void removeEntry(IUIRentalEntry rental) {
		rentals.remove(rental);
	}
	
	/**
	 * @param index of entry to remove from this model
	 */
	public void removeEntry(int index){
		removeEntry(rentals.get(index));
	}
	
	/**
	 * @param entry to remove from this model
	 */
	public void replaceEntry(IUIRentalEntry rental, int index) {
		rentals.set(index, rental);
	}
	
	/**
	 * @param entry to add to this model
	 */
	public void addEntry(IUIRentalEntry entry) {
		rentals.add(entry);
	}
}
