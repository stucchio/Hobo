//import org.styloot.hobo.*;
//Run with:
// $ java -cp build/classes:build/hobo.jar:/usr/local/lib/\*  FullSystemBenchmark

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.styloot.hobo.*;
import org.styloot.hobo.hoboindex.*;

public class FullSystemBenchmark {
    public static final double COLOR_DIST=10;
    private static Random random = new Random();

    private static CIELabColor randomColor() {
	return CIELabColor.CIELabFromRGB((int)Math.random()*255, (int)Math.random()*255, (int)Math.random()*255);
    }

    private static final int MAX_QUALITY=25;
    private static final int randomQuality() {
	return random.nextInt(MAX_QUALITY);
    }

    private static final int MAX_COST=25;
    private static final int randomCost() {
	return 1+random.nextInt(MAX_COST);
    }


    private static final String[] CATEGORIES = new String[] { "/dress", "/dress/short", "/dress/long", "/skirt", "/skirt/mini", "/skirt/hippie", "/footwear", "/footwear/boots", "/footwear/sandals" };
    private static String randomCategory() {
	return CATEGORIES[random.nextInt(CATEGORIES.length)];
    }

    private static final int NUM_RANDOM_FEATURES = 2500;
    private static String randomFeature() {
	return "feature-" + random.nextInt(NUM_RANDOM_FEATURES);
    }

    private static final int ODD_RANDOM_FEATURES = 2000;
    private static final double ODD_FEATURE_PROBABILITY = 0.5;
    private static final int NUM_ODD_FEATURES = 10;
    private static final int FEATURE_GROUPS = 3;
    private static final int FEATURE_GROUP_SIZE = 10;
    private static final int RANDOM_FEATURE_SET_MAX_SIZE=15;

    private static Vector<String> randomFeatureSet() {
	Vector<String> result = new Vector<String>();
	for (int i=0;i<FEATURE_GROUPS;i++) {
	    result.add("feature-"+i+"-"+random.nextInt(FEATURE_GROUP_SIZE));
	}
	if (random.nextDouble() < ODD_FEATURE_PROBABILITY) { //Half the time there are odd features, half the time it's standard
	    int numFeatures = random.nextInt(NUM_ODD_FEATURES);
	    for (int i=0;i<numFeatures;i++) {
		result.add("odd-feature-" + random.nextInt(ODD_RANDOM_FEATURES));
	    }
	}
	return result;
    }

    private static Vector<String> randomFeatureQuery() {
	Vector<String> result = new Vector<String>();
	if (random.nextDouble() > 0.5) {
	    int numGroups = random.nextInt(FEATURE_GROUPS);
	    for (int i=0;i<numGroups;i++) {
		result.add("feature-"+i+"-"+random.nextInt(FEATURE_GROUP_SIZE));
	    }
	}
	if (random.nextDouble() > 0.5) {
	    result.add("odd-feature-" + random.nextInt(ODD_RANDOM_FEATURES));
	}
	return result;
    }

    private static Vector<String> randomGroupedFeatureQuery() {
	Vector<String> result = new Vector<String>();
	if (random.nextDouble() > 0.5) {
	    int numGroups = random.nextInt(FEATURE_GROUPS);
	    for (int i=0;i<numGroups;i++) {
		result.add("feature-"+i+"-"+random.nextInt(FEATURE_GROUP_SIZE));
	    }
	}
	return result;
    }

    private static Item[] getItems(int numItems) {
	Item[] result = new Item[numItems];
	for (int i=0;i<numItems;i++) {
	    result[i] = new Item("id-" + i, randomCategory(), randomFeatureSet(), randomQuality(), randomColor(), randomCost());
	}
	return result;
    }

    private static Item runIterator(Iterator<Item> iterator) {
	Item item = null;
	for (int i=0;i<ITEMS_PER_PAGE;i++) {
	    if (iterator.hasNext()) {
		item = iterator.next();
	    }
	}
	return item;
    }

    public static final int NUM_ITEMS = (int)1e6;
    public static final int NUM_RUNS_PER_TEST=10000;
    public static final int ITEMS_PER_PAGE=50;

    public static double findByCategoryBenchmark(HoboIndex index) {
	long beginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS_PER_TEST;i++) {
	    Iterator<Item> iterator = index.find(randomCategory(), null, null, -1, 0, Integer.MAX_VALUE);
	    runIterator(iterator);
	}
	return ((double)(System.nanoTime() - beginTime)) / (NUM_RUNS_PER_TEST);
    }

    public static double findByFeaturesBenchmark(HoboIndex index) {
	long beginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS_PER_TEST;i++) {
	    Iterator<Item> iterator = index.find("", randomFeatureQuery(), null, -1, 0, Integer.MAX_VALUE);
	    runIterator(iterator);
	}
	return ((double)(System.nanoTime() - beginTime)) / NUM_RUNS_PER_TEST;
    }

    public static double findByFeaturesGroupedBenchmark(HoboIndex index) {
	long beginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS_PER_TEST;i++) {
	    Iterator<Item> iterator = index.find("", randomGroupedFeatureQuery(), null, -1, 0, Integer.MAX_VALUE);
	    runIterator(iterator);
	}
	return ((double)(System.nanoTime() - beginTime)) / NUM_RUNS_PER_TEST;
    }


    public static void main(String[] args) {
	HoboIndex index = new SimpleHoboIndex(getItems(NUM_ITEMS));
	CIELabColor baseColor = randomColor();
	System.out.println( "FindByCategory: " + findByCategoryBenchmark(index)/1000 + "us");
	System.out.println( "FindByFeatures: " + findByFeaturesBenchmark(index)/1000 + "us");
	System.out.println( "FindByFeaturesGrouped: " + findByFeaturesGroupedBenchmark(index)/1000 + "us");

    }
}