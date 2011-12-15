package org.styloot.hobo.iterators;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.*;

public abstract class FilterIterator implements Iterator<Item> {
    FilterIterator(Iterator<Item> iter) {
        iterator = iter;
    }
    boolean iterationStarted = false;
    Item nextItem = null;
    Iterator<Item> iterator;

    public abstract boolean predicate(Item i);

    public Item next() {
	startIterationIfNecessary();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Item currentItem = nextItem;
        nextItem = findNext();
        return currentItem;
    }

    public boolean hasNext() {
	startIterationIfNecessary();
        return (nextItem != null);
    }

    private Item findNext() {
        while (iterator.hasNext()) {
            Item i = iterator.next();
            if (predicate(i)) {
                return i;
            }
        }
        return null;
    }

    private void startIterationIfNecessary() {
	if (!iterationStarted) {
	    iterationStarted = true;
	    nextItem = findNext();
	}
    }

    public void remove() {
	throw new UnsupportedOperationException("Remove not implemented");
    }

}