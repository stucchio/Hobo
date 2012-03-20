//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.CIELabColor;

import java.util.*;

public class TestColor {
    public static final double TOLERANCE=1e-12;

    @Test public void testFromRGB(){
	CIELabColor c = CIELabColor.CIELabFromRGB(255,16,0);
	assertEquals(53.63283192339698, c.L, TOLERANCE);
	assertEquals(78.95144586146552, c.a, TOLERANCE);
	assertEquals(67.359200613973, c.b, TOLERANCE);
    }

    @Test public void testFromRGB2(){
	CIELabColor c = CIELabColor.CIELabFromRGB(25,16,125);
	assertEquals(15.788332034963975, c.L, TOLERANCE);
	assertEquals(40.974868070447116, c.a, TOLERANCE);
	assertEquals(-58.06751132781398, c.b, TOLERANCE);
    }

    @Test public void testDist(){
	CIELabColor c = CIELabColor.CIELabFromRGB(25,16,125);
	CIELabColor c_copy = CIELabColor.CIELabFromRGB(25,16,125);
	CIELabColor c2 = CIELabColor.CIELabFromRGB(255,16,0);
	assertEquals(c.distanceTo(c), 0, TOLERANCE);
	assertEquals(c.distanceTo(c_copy), 0, TOLERANCE);
	assertEquals(c.distanceTo(c2), 62.68225623073322, TOLERANCE);
	assertEquals(c2.distanceTo(c), 72.56500879371883, TOLERANCE);
    }

    @Test public void testDistVariations(){
	CIELabColor c = CIELabColor.CIELabFromRGB(25,16,125);
	CIELabColor c2 = CIELabColor.CIELabFromRGB(255,16,0);
	assertEquals(c.distance2To(c2), CIELabColor.distance2(c.L, c.a, c.b, c2.L, c2.a, c2.b), TOLERANCE);
	assertEquals(c.distance2To(c2), CIELabColor.distance2(c, c2.L, c2.a, c2.b), TOLERANCE);
    }
}