package iterators;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.*;

import java.util.*;

public class TestCombinedIterator {
    Vector<Vector<Item>> items = new Vector<Vector<Item>>();

    @Before public void setUp() {
	for (int j=0;j<3;j++) {
	    items.add(new Vector<Item>());
	}
	for (int j=0;j<3;j++) {
	    for (int i=0;i<10;i++) {
		Vector<String> features = new Vector<String>();
		features.add("foo");
		items.get(j).add( new Item("id" + i+"_" + j, "/baz", features, 10-i+j, null, 5) );
	    }
	}
    }

    @Test public void testIterator() {
	Vector<Iterator<Item>> iters = new Vector<Iterator<Item>>();
	for (int j=0;j<3;j++) {
	    iters.add(items.get(j).iterator());
	}
	Iterator<Item> iter = new CombinedIterator(iters);
	int itemsFound = 0;
	int lastQuality = Integer.MAX_VALUE;
	while (iter.hasNext()) {
	    Item item = iter.next();
	    assertTrue( item.quality <= lastQuality );
	    lastQuality = item.quality;
	    itemsFound += 1;
	}
	assertEquals(itemsFound, 30);
    }

}