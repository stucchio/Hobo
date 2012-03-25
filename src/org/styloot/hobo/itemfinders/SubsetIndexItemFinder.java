package org.styloot.hobo.itemfinders;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.*;
import org.styloot.hobo.iterators.*;

public class SubsetIndexItemFinder implements ItemFinder {
    public SubsetIndexItemFinder(Collection<Item> myItems, String cat) {
	items = new Vector<Item>(myItems);
	Collections.sort(items);
	category = cat;

	//First cook up map of FeatureSet to Items
	Map<FeatureSet,Vector<Item>> featureSetItems = new HashMap<FeatureSet,Vector<Item>>();
	for (Item i : items) {
	    FeatureSet f = new FeatureSet(i.features);
	    if (!featureSetItems.containsKey(f)) {
		featureSetItems.put(f, new Vector<Item>());
	    }
	    featureSetItems.get(f).add(i);
	}
	for (FeatureSet f : featureSetItems.keySet()) {
	    featuresToItemsMap.put(f, new VectorItemFinder(featureSetItems.get(f), cat));
	}

	//Now build index of Feature->FeatureSets
	for (FeatureSet f : featureSetItems.keySet()) {
	    for (Feature feature : f.features) {
		if (!featureToFeatureSetsMap.containsKey(feature)) {
		    featureToFeatureSetsMap.put(feature, new HashSet<FeatureSet>());
		}
		featureToFeatureSetsMap.get(feature).add(f);
	    }
	}
	for (Feature feature : featureToFeatureSetsMap.keySet()) { //Now convert from HashSets to vectors
	    Collection<FeatureSet> featureSets = featureToFeatureSetsMap.get(feature);
	    featureToFeatureSetsMap.put(feature, new Vector<FeatureSet>(featureSets));
	}
    }

    private Vector<Item> items;
    private String category;

    public int size() {
	return items.size();
    }

    public Iterator<Item> getItems() {
	return items.iterator();
    }

    public Iterator<Item> find(Collection<String> featuresAsStrings, CIELabColor color, double distance, int minPrice, int maxPrice) {
	if (featuresAsStrings == null || featuresAsStrings.size() == 0) {
	    return findNoFeatures(color, distance, minPrice, maxPrice);
	}
	Vector<Iterator<Item>> iterators = new Vector<Iterator<Item>>();
	Feature[] features = Feature.getFeatures(featuresAsStrings);

	for (FeatureSet f : getFeatureSetsContainingFeature(features)) {
	    iterators.add( featuresToItemsMap.get(f).find(null, color, distance, minPrice, maxPrice) );
	}
	if (iterators.size() > 1) {
	    return new CombinedIterator(iterators);
	}
	if (iterators.size() == 1) { //Avoid overhead of CombinedIterator if there is only 1
	    return iterators.get(0);
	}
	return new Iterator<Item>() { //An Anonymous null iterator class - has no items in it.
	    public boolean hasNext() { return false; }
	    public Item next() { throw new NoSuchElementException(); }
	    public void remove() { throw new UnsupportedOperationException("Remove not implemented"); }
	};
    };

    private Iterator<Item> findNoFeatures(CIELabColor color, double distance, int minPrice, int maxPrice) {
	Iterator<Item> iterator = getItems();
	iterator = CostFilterIterator.wrap(iterator, minPrice, maxPrice);
	iterator = ColorFilterIterator.wrap(iterator, color, distance);
	return iterator;
    }

    private Collection<FeatureSet> getFeatureSetsContainingFeature(Feature[] features) {
	//Precondition - features are *assumed* to be sorted
	//This will not work if they aren't.
	Collection<FeatureSet> smallestSetOfPossibleFeatureSets = null;
	int maxSize = Integer.MAX_VALUE;
	for (Feature feature : features) {
	    Collection<FeatureSet> featureCollection = featureToFeatureSetsMap.get(feature);
	    if (featureCollection == null) { //In this case, at least one of our features is not contained in any feature set.
		return new Vector<FeatureSet>(); //SmallestSetOfPossibleFeatureSets should be null here.
	    }
	    if (featureCollection.size() < maxSize) {
		maxSize = featureCollection.size();
		smallestSetOfPossibleFeatureSets = featureCollection;
	    }
	}

	if (smallestSetOfPossibleFeatureSets == null) {
	    return new Vector<FeatureSet>();
	}

	//We now have the minimal set of feature sets, i.e. every possible superset of features is contained in smallestSetOfPossibleFeatureSets.
	//Lets throw away the items that don't contain all features.
	Vector<FeatureSet> result = new Vector<FeatureSet>();
	for (FeatureSet f : smallestSetOfPossibleFeatureSets) {
	    if (f.containsSorted(features)) {
		result.add(f);
	    }
	}

	return result;
    }

    private final Map<FeatureSet,VectorItemFinder> featuresToItemsMap = new HashMap<FeatureSet,VectorItemFinder>();

    private final Map<Feature,Collection<FeatureSet>> featureToFeatureSetsMap = new HashMap<Feature,Collection<FeatureSet>>();

    private static class FeatureSet {
	public FeatureSet(Feature[] features) {
	    this.features = features;
	    Arrays.sort(this.features);
	}

	public boolean containsSorted(Feature[] feats) {
	    return Util.isSubsetSorted(feats, features);
	}

	@Override public int hashCode() {
	    int result = 0;
	    for (Feature f : features) {
		result += f.getId();
	    }
	    return result;
	}

	@Override public boolean equals(Object otherObj) {
	    FeatureSet other = (FeatureSet) otherObj;
	    if (features.length != other.features.length) {
		return false;
	    }
	    for (int i=0;i<features.length;i++) {
		if (features[i] != other.features[i]) {
		    return false;
		}
	    }
	    return true;
	}

	private Feature[] features;
    }

}