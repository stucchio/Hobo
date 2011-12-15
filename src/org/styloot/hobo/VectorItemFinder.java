package org.styloot.hobo;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.FeaturesFilterIterator;

public class VectorItemFinder implements ItemFinder {
    public VectorItemFinder(Collection<Item> myItems) {
	items = new Vector<Item>(myItems);
	Collections.sort(items);
    }
    public Vector<Item> items;

    public Iterator<Item> findItemsWithFeatures(Collection<String> features) {
	if (features == null)
	    return items.iterator();
	return new FeaturesFilterIterator(items.iterator(), features);
    };


    //Testing
    public static void main(String[] args) {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<10;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");

	    items.add(new Item("id" + i, "baz",	f, i));
	}

	ItemFinder itemFinder = new VectorItemFinder(items);
	Vector<String> f = new Vector<String>();
	f.add("bar");
	for (Iterator<Item> i = itemFinder.findItemsWithFeatures(f); i.hasNext(); ) {
	    Item item = (Item)i.next();
	    System.out.println(item.id + " -> " + item.quality);
	}

    }
}