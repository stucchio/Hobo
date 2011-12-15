package org.styloot.hobo;

import java.util.*;

public class CategoryMap extends TreeMap<String,ItemFinder> {
    public NavigableMap<String,ItemFinder> findSubCategoriesMap(String key) {
	return subMap(key, true, key+Character.MAX_VALUE, true);
    }

    public Collection<ItemFinder> itemFinders(String key) {
	return findSubCategoriesMap(key).values();
    }

    public Set<String> subcategories(String key) {
	return findSubCategoriesMap(key).keySet();
    }

    public static void main(String[] args) {
	CategoryMap c = new CategoryMap();
	c.put("/foo", null);
	c.put("/foo/bar", null);
	c.put("/foo/baz", null);
	c.put("/buz/", null);
	c.put("/buz/bag", null);
	System.out.println(c.subcategories("/buz"));
    }

}
