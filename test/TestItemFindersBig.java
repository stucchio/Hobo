//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.*;

import java.util.*;


@RunWith(Parameterized.class)
public class TestItemFindersBig {
    private static Random random = new Random();

    private static final int NUM_ITEMS=25000;
    ItemFinder finder;

    public TestItemFindersBig(ItemFinder finder) {
	this.finder = finder;
    }

    private static String itemId(int i) {
	return "id" + i;
    }

    private static int NUM_ODD_FEATURES = 500;
    private static double ODD_FEATURE_PROBABILITY = 0.35;
    private static Vector<Item> getItemList() {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<NUM_ITEMS;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");
	    if (i % 3 == 0)
		f.add("baz");
	    if (random.nextDouble() < ODD_FEATURE_PROBABILITY)
		f.add("odd-feature-" + random.nextInt(NUM_ODD_FEATURES));
	    items.add(new Item(itemId(i), "/baz", f, i, CIELabColor.CIELabFromRGB(0, i, 0), i % 5 + 1));
	}
	return items;
    }

    private static Vector<Item> iteratorToVector(Iterator<Item> iter) {
	Vector<Item> result = new Vector<Item>();
	while (iter.hasNext()) {
	    result.add(iter.next());
	}
	return result;
    }

    @Parameters public static Collection<Object[]> generateData() {
	Vector<Item> items = getItemList();
	return Arrays.asList(new Object[][] {
		new Object[]{ new VectorItemFinder(items, "/baz") },
		new Object[]{ new ShallowIndexItemFinder(items, "/baz") },
		new Object[]{ new SubsetIndexItemFinder(items, "/baz") },
	    });
    }

    @Test public void testFindBaz() {
	Vector<String> features = new Vector<String>();
	features.add("baz"); //Nonexistent feature
	Iterator<Item> iterator = finder.find( features, null, -1, 0, Integer.MAX_VALUE);

	int itemsFound = 0;
	while (iterator.hasNext()) {
	    Item item = iterator.next();
	    assertEquals(item.quality % 3, 0);
	    itemsFound += 1;
	}
	assertEquals(NUM_ITEMS/3+1, itemsFound);

    }
}