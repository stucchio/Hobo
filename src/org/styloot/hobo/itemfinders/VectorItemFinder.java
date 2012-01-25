package org.styloot.hobo.itemfinders;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.*;
import org.styloot.hobo.iterators.*;

public class VectorItemFinder implements ItemFinder {
    public VectorItemFinder(Collection<Item> myItems, String cat) {
	items = new Vector<Item>(myItems);
	Collections.sort(items);
	category = cat;
    }
    private Vector<Item> items;
    private String category;

    public int size() {
	return items.size();
    }

    public Iterator<Item> getItems() {
	return items.iterator();
    }

    public Iterator<Item> findItemsWithFeatures(Collection<String> features, int minPrice, int maxPrice) {
	if (features == null || features.size() == 0)
	    return CostFilterIterator.wrap(items.iterator(), minPrice, maxPrice);
	Iterator<Item> iterator = CostFilterIterator.wrap(items.iterator(), minPrice, maxPrice);
	return new FeaturesFilterIterator(iterator, features);
    };

    public Iterator<Item> findItemsWithFeaturesAndColor(Collection<String> features, CIELabColor color, double distance, int minPrice, int maxPrice) {
	if (color != null) {
	    return new ColorFilterIterator(findItemsWithFeatures(features, minPrice, maxPrice), color, distance);
	}
	return findItemsWithFeatures(features, minPrice, maxPrice);
    };

    //Testing
    public static void main(String[] args) {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<10;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");

	    items.add(new Item("id" + i, "baz",	f, i, null, 0));
	}

	ItemFinder itemFinder = new VectorItemFinder(items, "");
	Vector<String> f = new Vector<String>();
	f.add("bar");
	for (Iterator<Item> i = itemFinder.findItemsWithFeatures(f, 0, Integer.MAX_VALUE); i.hasNext(); ) {
	    Item item = (Item)i.next();
	}

    }
}