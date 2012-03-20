package iterators;

import org.junit.*;
import static org.junit.Assert.*;

import org.styloot.hobo.*;
import org.styloot.hobo.iterators.*;

import java.util.*;

public class TestColorFilterIterator {
    Vector<Item> items = new Vector<Item>();

    @Before public void setUp() {
	Vector<String> features = new Vector<String>();
	features.add("foo");
	features.add("bar");
	for (int i=0;i<10;i++) {
	    items.add( new Item("id" + i, "/baz", features, 3, CIELabColor.CIELabFromRGB(0, i, 0), 5) );
	}
    }

    @Test public void testColorFilter() {
	CIELabColor baseColor = CIELabColor.CIELabFromRGB(0, 0, 0);
	Iterator<Item> iter = new ColorFilterIterator(items.iterator(), baseColor, 2);
	int itemsFound = 0;
	while (iter.hasNext()) {
	    Item item = iter.next();
	    assertNotNull(item);
	    assertTrue("item " + item.id + ", dist=" + baseColor.distanceTo(item.color), baseColor.distanceTo(item.color) <= 2);
	    itemsFound += 1;
	}
	assertEquals(5, itemsFound);
    }


}