package org.styloot.hobo.iterators;

import java.util.*;

import org.styloot.hobo.*;

public class CombinedIterator implements Iterator<Item> {
    /*Contract:

      Precondition: The iters used to create this iterator are sorted according to item.quality (highest first).

      If this precondition is met, then the output of the CombinedIterator will also be sorted by item.quality.
     */
    public CombinedIterator(Collection<Iterator<Item>> iters) {
	for (Iterator<Item> iter : iters) {
	    if (iter.hasNext()) {
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

    public static void main(String[] args) {
	Vector<Vector<Item>> items = new Vector<Vector<Item>>();
	for (int i=0;i<3;i++) {
	    items.add(new Vector<Item>());
	}
	for (int i=0;i<30;i++) {
	    items.get(i % 3).add(new Item("id" + i, "baz", new Vector<String>(), i + (i % 5), null, 0));
	}
	Vector<Iterator<Item>> iterators = new Vector<Iterator<Item>>();
	for (int i=0;i<3;i++) {
	    Collections.sort(items.get(i));
	    iterators.add(items.get(i).iterator());
	}

	for (Iterator<Item> i = new CombinedIterator(iterators); i.hasNext(); ) {
	    Item item = (Item)i.next();
	    System.out.println(item.id + " -> " + item.quality);
	}

    }
}