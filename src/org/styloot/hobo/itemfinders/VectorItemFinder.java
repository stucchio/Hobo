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

    public Iterator<Item> find(Collection<String> features, CIELabColor color, double distance, int minPrice, int maxPrice) {
	Iterator<Item> iterator = items.iterator();
	iterator = CostFilterIterator.wrap(iterator, minPrice, maxPrice);
	iterator = FeaturesFilterIterator.wrap(iterator, features);
	iterator = ColorFilterIterator.wrap(iterator, color, distance);
	return iterator;
    };

}