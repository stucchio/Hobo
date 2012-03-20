package iterators;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.*;

import java.util.*;

public class TestFeaturesFilterIterator {
    Vector<Item> items = new Vector<Item>();

    @Before public void setUp() {
	for (int i=0;i<10;i++) {
	    Vector<String> features = new Vector<String>();
	    features.add("foo");
	    if (i % 2 == 0) {
		features.add("bar");
	    }
	    items.add( new Item("id" + i, "/baz", features, i, null, 5) );
	}
    }

    @Test public void testIterator() {
	Vector<String> features = new Vector<String>();
	features.add("foo");
	features.add("bar");
	Iterator<Item> iter = new FeaturesFilterIterator(items.iterator(), features);
	int itemsFound = 0;
	while (iter.hasNext()) {
	    Item item = iter.next();
	    assertNotNull(item);
	    assertEquals("item " + item.id, "id" + 2*itemsFound, item.id);
	    itemsFound += 1;
	}
	assertEquals(5, itemsFound);
    }

    @Test public void testIterator2() {
	Vector<String> features = new Vector<String>();
	features.add("foo");
	Iterator<Item> iter = new FeaturesFilterIterator(items.iterator(), features);
	int itemsFound = 0;
	while (iter.hasNext()) {
	    Item item = iter.next();
	    assertNotNull(item);
	    assertEquals("item " + item.id, "id" + itemsFound, item.id);
	    itemsFound += 1;
	}
	assertEquals(10, itemsFound);
    }

    @Test public void testIterator3() {
	Vector<String> features = new Vector<String>();
	features.add("foo");
	features.add("buz");
	Iterator<Item> iter = new FeaturesFilterIterator(items.iterator(), features);
	int itemsFound = 0;
	while (iter.hasNext()) {
	    assertTrue(false);
	}
	assertEquals(0, itemsFound);
    }

}