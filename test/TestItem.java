//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.Item;
import org.styloot.hobo.CIELabColor;
import org.styloot.hobo.Feature;

import java.util.*;

public class TestItem {
    Item item1;
    Item item2;
    Vector<String> features;
    private static double TOLERANCE = 1e-12;

    @Before public void setUp() {
	features = new Vector<String>();
	for (int i=0;i<10;i++) {
	    features.add("feature-" + i);
	}
	item1 = new Item("1", "/baz", features, 3, CIELabColor.CIELabFromRGB(255,16,0), 5);
	item2 = new Item("1", "/baz", (String[])null, 1, null, 5);
    }

    @Test public void testCompare(){
	assertTrue( item1.compareTo(item2) < 0 );
	assertTrue( item2.compareTo(item1) > 0 );
    }

    @Test public void testHasFeatures(){
	assertTrue( item1.hasFeatures(features) );
    }

    @Test public void testHasFeatures2(){
	Vector<String> f = new Vector<String>();
	for (int j=0;j<10;j++) {
	    if (Math.random() > 0.5) {
		f.add("feature-" + j);
	    }
	}
	assertTrue( item1.hasFeatures(f) ); //Contains a subset of features
    }

    @Test public void testHasFeatures3(){
	Vector<String> f = new Vector<String>();
	f.add("baz");
	assertTrue(!item1.hasFeatures(f)); //Does not contain baz
    }

    @Test public void testHasFeatures4(){
	Vector<String> f = new Vector<String>();
	assertTrue(item1.hasFeatures(f)); //Contains empty featureset
    }

    @Test public void testHasFeaturesNull(){
	Vector<String> f = new Vector<String>();
	f.add("buz");
	assertTrue(!item2.hasFeatures(f)); //Does not contain anything
    }

    @Test public void testHasFeaturesNull2(){
	Vector<String> f = new Vector<String>();
	assertTrue(item2.hasFeatures(f)); //Does contain nothing
    }

    @Test public void testHasFeaturesSorted() {
	Vector<String> f = new Vector<String>();
	for (int j=0;j<10;j++) {
	    if (Math.random() > 0.5) {
		f.add("feature-" + j);
	    }
	}
	assertTrue(item1.hasFeaturesSorted(Feature.getFeatures(f))); //Contains a subset of features
    }

    @Test public void testHasFeaturesSorted2() {
	Vector<String> f = new Vector<String>();
	for (int j=0;j<10;j++) {
	    if (Math.random() > 0.5) {
		f.add("feature-" + j);
	    }
	}
	f.add("buz");
	assertTrue(!item1.hasFeaturesSorted(Feature.getFeatures(f))); //Does not contain a subset of features
    }

    @Test public void testColorDistFrom() {
	CIELabColor color = CIELabColor.CIELabFromRGB(25,16,125);
	assertEquals(item1.colorDist2From(color), color.distance2To(item1.getColor()), TOLERANCE); //Does not contain a subset of features
    }


}