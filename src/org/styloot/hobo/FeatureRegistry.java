package org.styloot.hobo;

import java.util.*;

public class FeatureRegistry {
    private static Map<String,Integer> featureIds = new HashMap<String,Integer>();
    private static int count = 0;

    public static int featureId(String feature) {
	if (featureIds.containsKey(feature)) {
	    return featureIds.get(feature);
	} else {
	    featureIds.put(feature, count);
	    count += 1;
	    return (count - 1);
	}
    }

    public static int[] featureId(String[] features) {
	if (features == null) {
	    return null;
	}
	int[] result = new int[features.length];
	for (int i=0;i<features.length;i++) {
	    result[i] = featureId(features[i]);
	}
	return result;
    }

    public static int[] featureId(Collection<String> features) {
	return featureId(features.toArray(new String[0]));
    }

    public static int size() {
	return featureIds.size();
    }
}