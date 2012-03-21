import org.styloot.hobo.*;

import org.styloot.hobo.Item;
import java.util.*;

public class HasFeaturesBenchmark {
    public static final int NUM_RUNS = 100000;
    public static final int FEATURES_PER_ITEM = 10;
    public static final int FEATURES_PER_QUERY = 4;

    private static Vector<String> randomFeatureSet(int numFeatures) {
	Vector<String> f = new Vector<String>();
	for (int j=0;j<numFeatures;j++) {
	    if (Math.random() > 0.5) {
		f.add("feature-" + j);
	    }
	}
	return f;
    }

    private static Item[] getItems(int numItems) {
	Item[] result = new Item[numItems];
	for (int i=0;i<numItems;i++) {
	    result[i] = new Item("id-" + i, "/baz", randomFeatureSet(FEATURES_PER_ITEM), 3, null, 5);
	}
	return result;
    }

    public static void main(String[] args) {
	Item[] items = getItems(NUM_RUNS);
	Feature[] features = Feature.getFeatures(randomFeatureSet(FEATURES_PER_QUERY));

	long unsortedBeginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS;i++) {
	    items[i].hasFeatures(features);
	}
	double unsortedTime = ((double)(System.nanoTime() - unsortedBeginTime)) / NUM_RUNS;

	long sortedBeginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS;i++) {
	    items[i].hasFeaturesSorted(features);
	}
	double sortedTime = ((double)(System.nanoTime() - sortedBeginTime)) / NUM_RUNS;
	System.out.println("HasFeatures benchmark: ");
	System.out.println("Unsorted: " + unsortedTime + "ns");
	System.out.println("Sorted:   " + sortedTime + "ns");
    }
}