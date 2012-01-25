package org.styloot.hobo.iterators;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.FilterIterator;
import org.styloot.hobo.iterators.ColorFilterIterator;

public class CostFilterIterator extends FilterIterator {
    private static final Logger log = LoggerFactory.getLogger(CostFilterIterator.class);
    public CostFilterIterator(Iterator<Item> iter, int minCost, int maxCost) {
	super(iter);
	min = minCost;
	max = maxCost;
    }

    private final int min;
    private final int max;

    public boolean predicate(Item item) {
	return item.cost >= min && item.cost <= max;
    }
}