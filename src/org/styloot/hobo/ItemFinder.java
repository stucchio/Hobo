package org.styloot.hobo;

import java.util.*;

public interface ItemFinder {
    public Iterator<Item> findItemsWithFeatures(Collection<String> features);
    public Iterator<Item> getItems();
}