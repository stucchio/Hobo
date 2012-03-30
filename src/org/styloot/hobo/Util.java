package org.styloot.hobo;

import java.util.*;

public class Util {
    public static boolean isSubsetSorted(Feature[] feats, Feature[] features) {
	if (feats == null) {
	    return true;
	}
	if (features == null && feats.length > 0) { //We definitely don't have the feature
	    return false;
	}
	int i=0;
	int j=0;
	int matches = 0;
	while ((i < features.length) && (j < feats.length)) {
	    if (feats[j].compareTo(features[i]) < 0) {
		return false;
	    }
	    if (feats[j].compareTo(features[i]) > 0) {
		i++;
		continue;
	    }
	    if (feats[j] == features[i]) {
		matches++;
		j++;
		i++;
		continue;
	    }
	}
	if (matches == feats.length) {
	    return true;
	} else {
	    return false;
	}
    }

    public static <T> Collection<T> collectionFromArray(T[] items) {
	Vector<T> result = new Vector<T>();
	for (int i=0;i<items.length;i++) {
	    result.add(items[i]);
	}
	return result;
    }
}