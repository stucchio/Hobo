package org.styloot.hobo.thriftserver;

import org.apache.thrift.TException;
import org.styloot.hobo.gen.*;

import java.util.*;

class HoboServerImpl implements Hobo.Iface {
    public List<String> findByCategory(String category_name, int page) throws NoSuchCategoryException, NoSuchFeatureException, TException {
	return null;
    }

    public List<String> findByFeatures(List<String> features, int page) throws NoSuchCategoryException, NoSuchFeatureException, TException {
	return null;
    }
}
