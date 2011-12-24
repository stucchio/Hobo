package org.styloot.hobo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import org.styloot.hobo.*;

public class Item implements Comparable<Item>{
    public Item(String i, String c, Collection<String> f, int q) {
	id = i; category = Category.getCategory(c); quality = q;
	features = Feature.getFeatures(f);
    }

    public Item(String i, String c, String[] f, int q) {
	id = i; category = Category.getCategory(c); quality = q;
	features = Feature.getFeatures(f);
    }

    public boolean hasFeatures(Collection<String> feats) {
	log.warn("Calling item.hasFeatures(Collection<String> features) - will be innefficient.");
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

    public int compareTo(Item o) {
	if (quality != o.quality) {
	    return (o.quality - quality);
	}
	return id.compareTo(o.id);
    }

    private static final Logger log = LoggerFactory.getLogger(Item.class);

    public static void main(String[] args) {
	Vector<String> f = new Vector<String>();
	f.add("foo");
	f.add("bar");
	Item i = new Item("1", "/baz", f, 1);
	if (!i.hasFeatures(f)) {
	    System.out.println("Has features failed");
	}
	f.remove(0);
	if (!i.hasFeatures(f)) {
	    System.out.println("Has features failed");
	}
	f.add("baz");
	if (i.hasFeatures(f)) {
	    System.out.println("Has features failed");
	}
    }
}