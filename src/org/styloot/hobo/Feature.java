package org.styloot.hobo;

import java.util.*;

public class Feature implements Comparable<Feature> {
    public final String name;
    private final int id;
    private static int currentId;

    private Feature(String nm) {
	name = nm;
	id = currentId;
	currentId += 1;
    }

    public int getId() {
	return id;
    }

    public static Feature getFeature(String nm) {
	if (!features.containsKey(nm)) {
	    features.put(nm, new Feature(nm));
	}
	return features.get(nm);
    }

    public static Feature[] getFeatures(String[] features) {
	if (features == null)
	    return null;
	Feature[] result = new Feature[features.length];
	for (int i=0;i<features.length;i++) {
	    result[i] = getFeature(features[i]);
	}
	Arrays.sort(result);
	return result;
    }

    public static Feature[] getFeatures(Collection<String> features) {
	if (features == null)
	    return null;
	return getFeatures(features.toArray(new String[0]));
    }

    public String toString() {
	return name;
    }

    public int hashCode() {
	return name.hashCode();
    }

    public int compareTo(Feature o) {
	return (id - o.id);
    }

    public boolean equals(Object o) {
	return (id == ((Feature)o).id);
    }

    public static int count() {
	return features.size();
    }

    private static Map<String,Feature> features = new HashMap<String,Feature>();
}