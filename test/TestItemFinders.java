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
public class TestItemFinders {
    private static final int NUM_ITEMS=20;
    ItemFinder finder;

    public TestItemFinders(ItemFinder finder) {
	this.finder = finder;
    }

    private static String itemId(int i) {
	return "id" + i;
    }

    private static Vector<Item> getItemList() {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<NUM_ITEMS;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");
	    if (i % 3 == 0)
		f.add("baz");
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
	    });
    }

    public static void testResults(Iterator<Item> iterator, int[] expectedResultIds) {
	Vector<Item> results = iteratorToVector( iterator );
	int numResults = expectedResultIds.length;

	assertEquals( "Result had wrong number of elements.", numResults, results.size() );
	String[] expectedResultIdStr = new String[expectedResultIds.length];
	String[] actualResultIdStr = new String[expectedResultIds.length];
	for (int i=0;i<expectedResultIds.length;i++) {
	    expectedResultIdStr[i] = itemId(expectedResultIds[i]);
	    actualResultIdStr[i] = results.get(i).id;
	}
	assertArrayEquals("Item Id's incorrect", expectedResultIdStr, actualResultIdStr);

    }

    @Test public void testFindFoo() {
	Vector<String> features = new Vector<String>();
	features.add("foo");
	int[] resultIds = new int[20];
	for (int i=0;i<20;i++) {
	    resultIds[i] = 19-i;
	}
	testResults( finder.find( features, null, -1, 0, Integer.MAX_VALUE),  resultIds);
    }

    @Test public void testFind2() {
	Vector<String> features = new Vector<String>();
	features.add("buz"); //Nonexistent feature
	testResults( finder.find( features, null, -1, 0, Integer.MAX_VALUE), new int[]{} );
    }

    @Test public void testFindBaz() {
	Vector<String> features = new Vector<String>();
	features.add("baz");
	testResults( finder.find( features, null, -1, 0, Integer.MAX_VALUE),
		     new int[]{ 18, 15, 12, 9, 6, 3, 0 });
    }

    @Test public void testFindBarBaz() {
	Vector<String> features = new Vector<String>();
	features.add("baz");
	features.add("bar");
	testResults( finder.find( features, null, -1, 0, Integer.MAX_VALUE), new int[] { 18, 12, 6, 0 } );
    }

    @Test public void testFindByColor() {
	Vector<String> features = new Vector<String>();
	CIELabColor baseColor = CIELabColor.CIELabFromRGB(0, 0, 0);
	double maxColorDist = 4;
	Iterator<Item> iterator = finder.find( features, baseColor, maxColorDist, 0, Integer.MAX_VALUE);
	testResults(iterator, new int[] { 8, 7, 6, 5, 4, 3, 2, 1, 0});
    }

    @Test public void testFindByColorFeature() {
	Vector<String> features = new Vector<String>();
	features.add("bar");
	CIELabColor baseColor = CIELabColor.CIELabFromRGB(0, 0, 0);
	double maxColorDist = 4;
	Iterator<Item> iterator = finder.find( features, baseColor, maxColorDist, 0, Integer.MAX_VALUE);
	testResults(iterator, new int[] { 8, 6, 4, 2, 0});
    }

    @Test public void testFindByColorFeatureCost() {
	Vector<String> features = new Vector<String>();
	features.add("bar");
	CIELabColor baseColor = CIELabColor.CIELabFromRGB(0, 0, 0);
	double maxColorDist = 4;
	Iterator<Item> iterator = finder.find( features, baseColor, maxColorDist, 0, 3);
	testResults(iterator, new int[] { 6, 2, 0}); //Just like testFindByColorFeature, but items[8].cost = 4, and items[4].cost = 5, so they are eliminated
    }

    @Test public void testFindByColorFeatureCost2() {
	Vector<String> features = new Vector<String>();
	features.add("bar");
	CIELabColor baseColor = CIELabColor.CIELabFromRGB(0, 0, 0);
	double maxColorDist = 4;
	Iterator<Item> iterator = finder.find( features, baseColor, maxColorDist, 4, Integer.MAX_VALUE);
	testResults(iterator, new int[] { 8, 4 }); //Just like testFindByColorFeature, but items[8].cost = 4, and items[4].cost = 5, so they are eliminated
    }

    @Test public void testSize() {
	assertEquals(finder.size(), NUM_ITEMS);
	assertEquals(iteratorToVector(finder.getItems()).size(), NUM_ITEMS);
    }

}