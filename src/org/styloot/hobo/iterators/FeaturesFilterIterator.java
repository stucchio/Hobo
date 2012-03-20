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

    public boolean predicate(Item item) {
	return item.hasFeaturesSorted(features); //Better performance, O(n+m) rather than O(N*M)
    }
}