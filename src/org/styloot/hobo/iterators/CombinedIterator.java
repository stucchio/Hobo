package org.styloot.hobo.iterators;

import java.util.*;

import org.styloot.hobo.*;

public class CombinedIterator implements Iterator<Item> {
    /*Contract:
      The CombinedIterator takes a collection of item iterators and returns a single iterator which contains the
      output of all the input iterators.

      Precondition: The iterators used to create this iterator are sorted according to item.quality (highest first).
      I.e., repeated calls to iterator.next() result in items of lower quality.

      If this precondition is met, then the output of the CombinedIterator will also be sorted by item.quality.
     */
    public CombinedIterator(Collection<Iterator<Item>> iters) {
	for (Iterator<Item> iter : iters) {
	    if (iter instanceof CombinedIterator) {
		addCombinedIterator( (CombinedIterator)iter );
	    } else if (iter.hasNext()) {
		queue.add(new ItemIteratorPair(iter.next(), iter));
	    }
	}
    }

    PriorityQueue<ItemIteratorPair> queue = new PriorityQueue<ItemIteratorPair>();

    public Item next() {
	if (queue.peek() == null)
	    throw new NoSuchElementException();

	ItemIteratorPair pair = queue.poll();
	if (pair.iter.hasNext()) {
	    queue.add(new ItemIteratorPair(pair.iter.next(), pair.iter));
	}
	return pair.item;
    }

    public boolean hasNext() {
	return (queue.peek() != null);
    }

    public void remove() {
	throw new UnsupportedOperationException("Remove not implemented");
    }

    //This is destructive on it's argument
    private void addCombinedIterator(CombinedIterator iterator) {
	Iterator<ItemIteratorPair> pairIterator = iterator.queue.iterator();
	while (pairIterator.hasNext()) {
	    ItemIteratorPair pair = pairIterator.next();
	    queue.add(pair);
	}
    }

    private static class ItemIteratorPair implements Comparable<ItemIteratorPair> {
	public Item item;
	public Iterator<Item> iter;
	ItemIteratorPair(Item i, Iterator<Item> it) {
	    item = i;
	    iter = it;
	}

	public int compareTo(ItemIteratorPair o) {
	    return item.compareTo(o.item);
	}
    }
}