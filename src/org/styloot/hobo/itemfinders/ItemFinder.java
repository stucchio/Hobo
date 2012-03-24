package org.styloot.hobo.itemfinders;

import java.util.*;

import org.styloot.hobo.*;

public interface ItemFinder {
    public Iterator<Item> find(Collection<String> features, CIELabColor c, double d, int minPrice, int maxPrice);
    public Iterator<Item> getItems();
    public int size();
}