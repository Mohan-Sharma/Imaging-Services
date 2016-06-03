package com.nzion.service.common;

import java.util.Collection;
import java.util.List;

/**
 * @author Sandeep Prusty
 * Aug 10, 2010
 */
public interface GenericHomeScreenService extends GenericHomeScreenSearchService {
	
	<T> T save(T t);

	<T> T delete(T t);
	
	<T> List<T> getLatestUpdateds(Class<T> klass, int count);
	
	void delete(Collection<?> collection);

	void activate(Collection<?> collection);
	
	void deActivate(Collection<?> collection, String reason);
}
