package org.styloot.hobo;

import java.util.*;

public interface ItemFinder {
    public Iterator findItemsWithFeatures(Collection<String> features);
}