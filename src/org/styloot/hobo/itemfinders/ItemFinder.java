package org.styloot.hobo.itemfinders;

import java.util.*;

import org.styloot.hobo.*;

public interface ItemFinder {
    public Iterator<Item> findItemsWithFeatures(Collection<String> features);
    public Iterator<Item> findItemsWithFeaturesAndColor(Collection<String> features, CIELabColor c, double d);
    public Iterator<Item> getItems();
    public int size();
}