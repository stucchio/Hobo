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

    public Iterator<Item> find(Collection<String> features, CIELabColor color, double distance, int minPrice, int maxPrice) {
	//If we have a best feature, just defer to that one's ItemFinder
	Feature bff = bestFinderFeature(features);
	if (bff != null) {
	    features.remove(bff.name);
	    return featureIndex.get(bff).find(features, color, distance, minPrice, maxPrice);
	}
	//Otherwise, we just iterate over our own items.
	Iterator<Item> iterator = items.iterator();
	iterator = CostFilterIterator.wrap(iterator, minPrice, maxPrice);
	iterator = FeaturesFilterIterator.wrap(iterator, features);
	iterator = ColorFilterIterator.wrap(iterator, color, distance);
	return iterator;
    }

    private Feature bestFinderFeature(Collection<String> features) {
	// Finds the feature corresponding to the ItemFinder with the fewest items.
	if (features == null || features.size() == 0) {
	    return null;
	}

	int minSize = Integer.MAX_VALUE;
	ItemFinder bestFinder = null;
	Feature bestFeature = null;

	for (String f : features) {
	    Feature feature = Feature.getFeature(f);
	    ItemFinder finder = featureIndex.get(feature);
 	    if ((finder != null) && (finder.size() < minSize)) {
		bestFinder = finder;
		minSize = bestFinder.size();
		bestFeature = feature;
	    }
	}
	return bestFeature;
    }
}