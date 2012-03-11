package jverleihnix.model;

import java.util.*;

public class ComparatorChain<E> implements Comparator<E>{
	private List<Comparator<E>> comparatorChain = new ArrayList<Comparator<E>>();
	
	public ComparatorChain(Comparator<E>... comparators){
		Collections.addAll( comparatorChain, comparators );
	}
	
	public void addComparator( Comparator<E> comparator ){		
		comparatorChain.add( comparator );
	}

	public int compare(E o1, E o2) {
		
		for ( Comparator<E> comparator : comparatorChain ){
			int order = comparator.compare(o1, o2 );
				if(order!=0){
					return order;
				}
		}
		return 0;
	}
	

}