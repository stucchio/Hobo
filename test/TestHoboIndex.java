//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.styloot.hobo.*;
import org.styloot.hobo.hoboindex.*;

import java.util.*;

@RunWith(Parameterized.class)
public class TestHoboIndex {
    private static final int NUM_ITEMS=20;
    HoboIndex index;

    public TestHoboIndex(HoboIndex index) {
	this.index = index;
    }

    private static String itemId(int i) {
	return "id" + i;
    }

    private static Vector<Item> getItemList() {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<NUM_ITEMS;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("buttons");
	    if (i % 2 == 0) {
		f.add("cleaveage");
	    }
	    CIELabColor color = CIELabColor.CIELabFromRGB(0, i, 0);
	    if (i % 3 == 0)
		items.add(new Item("id" + i, "/dress", f, i, color, 1+(i%5)));
	    if (i % 3 == 1)
		items.add(new Item("id" + i, "/dress/short", f, i, color, 1+(i%5)));
	    if (i % 3 == 2)
		items.add(new Item("id" + i, "/skirt", f, i, color, 1+(i%5)));
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
		new Object[]{ new SimpleHoboIndex(items) },
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

    @Test public void testFindDresses() {
	testResults( index.find("/dress", null, null, -1, 0, Integer.MAX_VALUE), new int[]{ 19, 18, 16, 15, 13, 12, 10, 9, 7, 6, 4, 3, 1, 0 } );
    }

    @Test public void testFindDressesWithFeature() {
	Vector<String> f = new Vector<String>();
	f.add("cleaveage");
	testResults( index.find("/dress", f, null, -1, 0, Integer.MAX_VALUE), new int[]{ 18, 16, 12,  10, 6, 4, 0 } );
    }

    @Test public void testFindDressesWithFeature2() {
	Vector<String> f = new Vector<String>();
	f.add("cleaveage");
	testResults( index.find("/dress/short", f, null, -1, 0, Integer.MAX_VALUE), new int[]{ 16, 10, 4 } );
    }

    @Test public void testFindDressesWithFeatureColor() {
	Vector<String> f = new Vector<String>();
	f.add("cleaveage");
	CIELabColor color = CIELabColor.CIELabFromRGB(0, 0, 0);
	testResults( index.find("/dress", f, color, 4, 0, Integer.MAX_VALUE), new int[]{ 6, 4, 0 } );
    }


}