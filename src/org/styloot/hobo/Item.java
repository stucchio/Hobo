package org.styloot.hobo;

import java.util.*;

public class Item implements Comparable<Item>{
    public Item(String i, String c, Collection<String> f, int q) {
	id = i; category = c; quality = q;
	features.addAll(f);
    }

    public boolean hasFeatures(Collection<String> feats) {
	if (feats == null) {
	    System.out.println("received null features");
	    return true;
	}
	for (String f : feats) {
	    boolean found = false;
	    for (String f2 : features) {
		if (f == f2) {
		    found = true;
		    break;
		}
	    }
	    if (!found) {
		return false;
	    }
	}
	return true;
    }

    public String id;
    public String category;
    public Vector<String> features = new Vector<String>();
    public int quality;

    public int compareTo(Item o) {
	if (quality != o.quality) {
	    return (o.quality - quality);
	}
	return id.compareTo(o.id);
    }

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