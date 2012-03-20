//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.Feature;

import java.util.*;

public class TestFeature {
    @Test public void testFeatureEquality() {
	Feature f = Feature.getFeature("foo");
	Feature f2 = Feature.getFeature("foo");
	assertTrue(f == f2);
    }

    @Test public void testGetFeatures() {
	Feature[] features = Feature.getFeatures(new String[] {"foo", "bar", "baz" });
	Feature[] featuresSorted = new Feature[] { Feature.getFeature("foo"), Feature.getFeature("bar"), Feature.getFeature("baz") };
	Arrays.sort(featuresSorted); //Should be sorted, albeit arbitrarily sorted.
	assertArrayEquals(features, featuresSorted);
    }

}