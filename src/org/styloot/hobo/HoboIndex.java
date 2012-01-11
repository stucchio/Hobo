package org.styloot.hobo;

import java.util.*;
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.styloot.hobo.*;
import org.styloot.hobo.itemfinders.ItemFinder;
import org.styloot.hobo.itemfinders.ShallowIndexItemFinder;
import org.styloot.hobo.itemfinders.VectorItemFinder;
import org.styloot.hobo.iterators.*;

public class HoboIndex {
    private static final Logger log = LoggerFactory.getLogger(HoboIndex.class);
    public HoboIndex(Collection<Item> items) {
	this(items.iterator());
    }

    public HoboIndex(Iterator<Item> items) {
	Map<String,List<Item>> catToItems = categoriesToItems(items);
	log.info("Initializing HoboIndex with " + catToItems.size() + " categories.");
	//Now we need to build ItemFinders
	for (String cat : catToItems.keySet()) {
	    categoryMap.put(cat, new ShallowIndexItemFinder(catToItems.get(cat), cat));
	}
	log.info("ItemFinders built.");
    }

    public Iterator<Item> find(String cat, Collection<String> features) {
	return findByColor(cat, features, (CIELabColor)null, 0.0);
    }

    public Iterator<Item> findByColor(String cat, Collection<String> features, CIELabColor color, double dist) {
	Vector<Iterator<Item>> iters = new Vector<Iterator<Item>>();

	Collection<ItemFinder> categories;
	if ((cat != "") && (cat != null)) {
	    categories = categoryMap.itemFinders(cat);
	} else {
	    categories = categoryMap.values(); //Small performance improvement in case of no category
	}
	for (ItemFinder f : categories) {
	    iters.add(f.findItemsWithFeaturesAndColor(features, color, dist));
	}
	return new CombinedIterator(iters);
    }

    private CategoryMap categoryMap = new CategoryMap();

    protected Map<String,List<Item>> categoriesToItems(Iterator<Item> items) {
	Map<String,List<Item>> result = new HashMap<String,List<Item>>();

	for (Iterator<Item> iter = items; iter.hasNext();) {
	    Item item = iter.next();
	    if (!result.containsKey(item.category.name)) {
		result.put(item.category.name, new LinkedList<Item>());
	    }
	    result.get(item.category.name).add(item);
	}
	return result;
    }

    private static class CategoryMap extends TreeMap<String,ItemFinder> {
	public NavigableMap<String,ItemFinder> findSubCategoriesMap(String key) {
	    return subMap(key, true, key+Character.MAX_VALUE, true);
	}

	public Collection<ItemFinder> itemFinders(String key) {
	    return findSubCategoriesMap(key).values();
	}

	public Set<String> subcategories(String key) {
	    return findSubCategoriesMap(key).keySet();
	}
    }

    public static void main(String[] args) {
	Vector<Item> items = new Vector<Item>();
	for (int i=0;i<10;i++) {
	    Vector<String> f = new Vector<String>();
	    f.add("foo");
	    if (i % 2 == 0)
		f.add("bar");
	    if (i % 3 == 0)
		items.add(new Item("id" + i, "/dress", f, i, null));
	    if (i % 3 == 1)
		items.add(new Item("id" + i, "/dress/short", f, i, null));
	    if (i % 3 == 2)
		items.add(new Item("id" + i, "/skirt", f, i, null));
	}

	HoboIndex idx = new HoboIndex(items);

	Vector<String> f = new Vector<String>();
	f.add("foo");
	f.add("bar");
	for (Iterator<Item> i = idx.find("/dress", f); i.hasNext(); ) {
	    Item item = (Item)i.next();
	    System.out.println(item.id + " -> " + item.category + " , " + item.quality);
	}

    }
}