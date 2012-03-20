package org.styloot.hobo;

import java.util.*;

public class Feature implements Comparable<Feature> {
    public final String name;

    private Feature(String nm) {
	name = nm;
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
	return name.compareTo(o.name);
    }

    public boolean equals(Object o) {
	return ((Feature)o).name.equals(name);
    }

    public static int count() {
	return features.size();
    }

    private static Map<String,Feature> features = new HashMap<String,Feature>();
}