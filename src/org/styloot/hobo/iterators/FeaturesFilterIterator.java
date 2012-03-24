package org.styloot.hobo.iterators;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.FilterIterator;

public class FeaturesFilterIterator extends FilterIterator {
    public FeaturesFilterIterator(Iterator<Item> iter, Collection<String> f) {
	super(iter);
	features = Feature.getFeatures(f); //getFeatures returns sorted
    }
    private Feature[] features;

    public static Iterator<Item> wrap(Iterator<Item> iterator, Collection<String> features) {
	if (features != null && features.size() > 0) {
	    iterator = new FeaturesFilterIterator(iterator, features);
	}
	return iterator;
    }

    public boolean predicate(Item item) {
	return item.hasFeaturesSorted(features); //Better performance, O(n+m) rather than O(N*M)
    }
}