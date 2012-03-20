//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.Item;

import java.util.*;

public class TestItem {
    Item item1;
    Item item2;
    Vector<String> features;

    @Before public void setUp() {
	features = new Vector<String>();
	features.add("foo");
	features.add("bar");
	item1 = new Item("1", "/baz", features, 3, null, 5);
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
	f.add("foo");
	assertTrue( item1.hasFeatures(f) ); //Contains a subset of "foo", "bar"
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
}