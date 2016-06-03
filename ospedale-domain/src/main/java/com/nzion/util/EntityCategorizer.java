package com.nzion.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nzion.domain.annot.SystemAuditable;

/**
 * @author Sandeep Prusty
 * Jun 30, 2010
 */
public interface EntityCategorizer {
	
	String PURGEABLE_ENTITIES = "PURGEABLE_ENTITIES";
	
	void categorize(Map<String, List<Class<?>>> holder, Set<String> classNames) throws ClassNotFoundException ;
	
	EntityCategorizer[] CATEGORIZERS = new EntityCategorizer[]{
		new EntityCategorizer() {
			public void categorize(Map<String, List<Class<?>>> holder, Set<String> classNames) throws ClassNotFoundException {
				char alphabet = 'A';
				for(int i = 0; i < 26 ; ++i, ++alphabet)
					holder.put(String.valueOf(alphabet), new LinkedList<Class<?>>());
				for(String className : classNames){
					Class<?> klass = Class.forName(className);
					if(!klass.isAnnotationPresent(SystemAuditable.class))
						holder.get(klass.getSimpleName().substring(0, 1)).add(klass);
				}
			}
		},
		
		new EntityCategorizer() {
			public void categorize(Map<String, List<Class<?>>> holder, Set<String> classNames) throws ClassNotFoundException {
			holder.remove(PURGEABLE_ENTITIES);
			List<Class<?>> entities = new LinkedList<Class<?>>();
			for(String className : classNames){
				Class<?> klass = Class.forName(className);
				if(com.nzion.domain.Historical.class.isAssignableFrom(klass))
					entities.add(klass);
			}
			holder.put(PURGEABLE_ENTITIES, entities);
			}
		}
	};

}