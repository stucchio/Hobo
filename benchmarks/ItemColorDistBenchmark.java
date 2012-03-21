import org.styloot.hobo.*;

import java.util.*;

public class ItemColorDistBenchmark {
    public static final int NUM_RUNS = 100000;
    public static final double COLOR_DIST=10;

    private static CIELabColor randomColor() {
	return CIELabColor.CIELabFromRGB((int)Math.random()*255, (int)Math.random()*255, (int)Math.random()*255);
    }

    private static Item[] getItems(int numItems) {
	Item[] result = new Item[numItems];
	for (int i=0;i<numItems;i++) {

	    result[i] = new Item("id-" + i, "/baz", (String[])null, 3, randomColor(), 5);
	}
	return result;
    }

    public static void main(String[] args) {
	Item[] items = getItems(NUM_RUNS);
	CIELabColor baseColor = randomColor();

	long beginTime = System.nanoTime();
	for (int i=0;i<NUM_RUNS;i++) {
	    items[i].colorDist2From(baseColor);
	}
	double time = ((double)(System.nanoTime() - beginTime)) / NUM_RUNS;

	System.out.println("ColorDist benchmark: ");
	System.out.println("Basic: " + time + "ns");

    }
}