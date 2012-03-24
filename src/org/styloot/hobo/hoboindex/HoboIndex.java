package org.styloot.hobo.hoboindex;

import java.util.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.ItemFinder;
import org.styloot.hobo.itemfinders.ShallowIndexItemFinder;
import org.styloot.hobo.itemfinders.VectorItemFinder;
import org.styloot.hobo.iterators.*;

public interface HoboIndex {
    public Iterator<Item> find(String cat, Collection<String> features, CIELabColor color, double dist, int minPrice, int maxPrice);
}