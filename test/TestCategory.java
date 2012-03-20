//package org.styloot.hobo.test;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.Category;

import java.util.*;

public class TestCategory {
    @Test public void testCategoryEquality() {
	Category c = Category.getCategory("foo");
	Category c2 = Category.getCategory("foo");
	assertTrue(c == c2);
    }
}