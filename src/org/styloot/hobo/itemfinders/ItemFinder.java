package org.styloot.hobo.itemfinders;

import java.util.*;

import org.styloot.hobo.*;

public interface ItemFinder {
    public Iterator<Item> findItemsWithFeatures(Collection<String> features);
    public Iterator<Item> getItems();
    public int size();
}