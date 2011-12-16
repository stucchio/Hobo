package org.styloot.hobo.thriftserver;

import org.apache.thrift.TException;
import org.styloot.hobo.gen.*;
import org.styloot.hobo.*;

import java.util.*;

class HoboServerImpl implements Hobo.Iface {
    public HoboServerImpl(HoboIndex idx, int ps) {
	index = idx;
	pageSize = ps;
    }
    HoboIndex index;
    int pageSize;

    public List<String> find(String category_name, List<String> features, int page) throws TException {
	Vector<String> result = new Vector<String>(pageSize);
	int count = 0;
	Iterator<Item> iter = index.find(category_name, features);
	while (iter.hasNext()) {
	    Item item = iter.next();
	    if (count >= page*pageSize) {
		result.add(item.id);
	    }
	    if (count > (page+1)*pageSize) {
		break;
	    }
	    count += 1;
	}
	return result;
    }
}
