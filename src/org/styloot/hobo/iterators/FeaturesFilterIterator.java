package org.styloot.hobo.iterators;

import java.util.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.FilterIterator;

public class FeaturesFilterIterator extends FilterIterator {
    public FeaturesFilterIterator(Iterator<Item> iter, Collection<String> f) {
	super(iter);
        featureIds = FeatureRegistry.featureId(f);
    }
    private int[] featureIds;

    public boolean predicate(Item item) {
	return item.hasFeatures(featureIds);
    }
}