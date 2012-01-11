package org.styloot.hobo.iterators;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.FilterIterator;
import org.styloot.hobo.iterators.ColorFilterIterator;

public class ColorFilterIterator extends FilterIterator {
    private static final Logger log = LoggerFactory.getLogger(ColorFilterIterator.class);
    public ColorFilterIterator(Iterator<Item> iter, CIELabColor c, double d) {
	super(iter);
	color = c;
	distanceSquared = d*d;
	if (d <= 0) {
	    log.warn("Received findItemsWithFeaturesAndColor query with distance <= 0. Using absolute value instead. Distance given was " + d);
	}
    }
    private final CIELabColor color;
    private final double distanceSquared;

    public boolean predicate(Item item) {
	if (item.color == null) {
	    return false;
	}
	return color.distance2To(item.color) <= distanceSquared;
    }
}