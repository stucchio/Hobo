package org.styloot.hobo.itemfinders;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.*;
import org.styloot.hobo.iterators.*;

public class ShallowIndexItemFinder implements ItemFinder {
    private static final Logger log = LoggerFactory.getLogger(ShallowIndexItemFinder.class);

    public ShallowIndexItemFinder(Collection<Item> myItems, String cat) {
	items = new Vector<Item>(myItems);
	Collections.sort(items);
	category = cat;
	Set<Feature> features = new HashSet<Feature>();
	for (Item item : items) {
	    if (item.getFeatures() != null) {
		for (Feature feature : item.getFeatures()) {
		    features.add(feature);
		}
	    }
	}
	featureIndex = new HashMap<Feature,ItemFinder>();
	for (Feature feature : features) {
	    Vector<Item> indexItems = new Vector<Item>();
	    int itemsAdded = 0;
	    for (Item i : items) {
		if (i.hasFeature(feature)) {
		    indexItems.add(i);
		    itemsAdded += 1;
		}
	    }
	    if (itemsAdded > 0) {
		featureIndex.put(feature, new VectorItemFinder(indexItems, category));
	    }
	}
    }
    private final Map<Feature,ItemFinder> featureIndex;
    private final Vector<Item> items;
    private final String category;

    public int size() {
	return items.size();
    }

    public Iterator<Item> getItems() {
	return items.iterator();
    }

    public Iterator<Item> findItemsWithFeatures(Collection<String> features, int minPrice, int maxPrice) {
	if (features == null || features.size() == 0) {
	    return CostFilterIterator.wrap(items.iterator(), minPrice, maxPrice);
	}

	features = new Vector<String>(features); //Clone this, because we will mutate it later

	int minSize = Integer.MAX_VALUE;
	ItemFinder bestFinder = this;
	String bestFeature = null;

	for (String f : features) {
	    Feature feature = Feature.getFeature(f);
	    ItemFinder finder = featureIndex.get(feature);
 	    if ((finder != null) && (finder.size() < minSize)) {
		bestFinder = finder;
		minSize = bestFinder.size();
		bestFeature = f;
	    }
	}
	if (bestFinder == this) {
	    Iterator<Item> iterator = CostFilterIterator.wrap(items.iterator(), minPrice, maxPrice);
	    return new FeaturesFilterIterator(iterator, features);
	}

	features.remove(bestFeature);
	return bestFinder.findItemsWithFeatures(features, minPrice, maxPrice);
    };

    public Iterator<Item> findItemsWithFeaturesAndColor(Collection<String> features, CIELabColor color, double distance, int minPrice, int maxPrice) {
	if (color == null || distance < 0) {
	    return findItemsWithFeatures(features, minPrice, maxPrice);
	}
	return new ColorFilterIterator(findItemsWithFeatures(features, minPrice, maxPrice), color, distance);
    }

    //Testing
    public static void main(String[] args) {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<10;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");
	    items.add(new Item("id" + i, "/clothing",	f, i, null, 0));
	}

	ItemFinder itemFinder = new ShallowIndexItemFinder(items, "clothing");
	Vector<String> f = new Vector<String>();
	f.add("bar");
	int id=8;
	for (Iterator<Item> iterator = itemFinder.findItemsWithFeatures(f, 0, Integer.MAX_VALUE); iterator.hasNext(); ) {
	    Item item = (Item)iterator.next();
	    if (!item.id.equals("id"+id)) {
		System.out.println("Error - should be id" + id + ", was " + item.id);
	    }
	    id -= 2;
	}

    }
}