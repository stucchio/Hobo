package org.styloot.hobo;

import java.util.*;

public class Category implements Comparable<Category> {
    public final String name;

    private Category(String nm) {
	name = nm;
    }

    public static Category getCategory(String nm) {
	if (!categories.containsKey(nm)) {
	    categories.put(nm, new Category(nm));
	}
	return categories.get(nm);
    }

    public String toString() {
	return name;
    }

    public int compareTo(Category o) {
	return name.compareTo(o.name);
    }

    public boolean equals(Object o) {
	return ((Category)o).name.equals(name);
    }

    private static Map<String,Category> categories = new HashMap<String,Category>();
}