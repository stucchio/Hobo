package org.styloot.hobo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import org.styloot.hobo.*;

public class Item implements Comparable<Item>{
    public Item(String i, String c, Collection<String> f, int q, CIELabColor clr, int cst) {
	id = i; category = Category.getCategory(c); quality = q;
	features = Feature.getFeatures(f);
	color = clr;
	cost = cst;
    }

    public Item(String i, String c, String[] f, int q, CIELabColor clr, int cst) {
	id = i; category = Category.getCategory(c); quality = q;
	features = Feature.getFeatures(f);
	color = clr;
	cost = cst;
    }

    public boolean hasFeatures(Collection<String> feats) {
	log.warn("Calling item.hasFeatures(Collection<String> features) - will be inefficient.");
	if (feats == null) {
	    return true;
	}
	return hasFeatures(Feature.getFeatures(feats));
    }

    public Feature[] getFeatures() {
	return features;
    }

    public boolean hasFeature(Feature f) {
	for (Feature f2 : features) {
	    if (f == f2) {
		return true;
	    }
	}
	return false;
    }

    public boolean hasFeatures(Feature[] feats) {
	if (feats == null) {
	    return true;
	}
	if (features == null && feats.length > 0) { //We definitely don't have the feature
	    return false;
	}
	for (Feature f : feats) {
	    if (!hasFeature(f)) {
		return false;
	    }
	}
	return true;
    }

    public final String id;
    public final Category category;
    public final Feature[] features;
    public final int quality;
    public final CIELabColor color;
    public final int cost;

    public int compareTo(Item o) {
	if (quality != o.quality) {
	    return (o.quality - quality);
	}
	return id.compareTo(o.id);
    }

    private static final Logger log = LoggerFactory.getLogger(Item.class);
}