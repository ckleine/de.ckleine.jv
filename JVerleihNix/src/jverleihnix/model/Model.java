package jverleihnix.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jverleihnix.ui.IUIRentalEntry;
import jverleihnix.ui.MediaType;

import java.util.Comparator;

public class Model {
	private List<IUIRentalEntry> rentals;
	
	/**
	 * Singleton field to access this model.
	 */
	public final static Model instance = new Model();
	
	private Model() {
		rentals = new ArrayList<IUIRentalEntry>();

	}
	/**
	 * @return List with rentals
	 */
	public List<IUIRentalEntry> getEntries() {
		return rentals;
	}
	
	/**
	 * @param rentals List to set to this model
	 */
	public void setEntries(List<IUIRentalEntry> rentals) {
		this.rentals = rentals;
	}
	
	/**
	 * @param rental to remove from this model
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
		sort();
	}


	@SuppressWarnings("unchecked")
	private void sort() {
		Collections.sort(rentals, new ComparatorChain<IUIRentalEntry>(
				DATE_COMPARATOR, MEDIA_TYPE_COMPARATOR));
	}
	
	private final static Comparator<IUIRentalEntry>
		DATE_COMPARATOR = new Comparator<IUIRentalEntry>(){
		public int compare(IUIRentalEntry rental1, IUIRentalEntry rental2){
			if (Validation.getDate(rental1.getDueDate()).before(
					Validation.getDate(rental2.getDueDate()))) {
				return -1;
			}else if (Validation.getDate(rental1.getDueDate()).after(Validation.getDate(rental2.getDueDate()))){
				return 1;
			}
			return 0;
		}
	};
		
	private final static Comparator<IUIRentalEntry>
		MEDIA_TYPE_COMPARATOR = new Comparator<IUIRentalEntry>(){
		public int compare(IUIRentalEntry rental1, IUIRentalEntry rental2){
			if (MediaTypeToInt(rental1.getMediaType()) > MediaTypeToInt(rental2.getMediaType())){
				return -1;
			}else if(MediaTypeToInt(rental1.getMediaType()) == MediaTypeToInt(rental2.getMediaType())){
				return 0;
			}
			return 1;
		}
	};
	
	private static int MediaTypeToInt(MediaType mediaType){
		switch(mediaType){
		case CD:
			return 1;
		case DVD:
			return 2;
		case BLUERAY:
			return 3;
		default:
			return 0;
		}
	}
	
}
