package com.nzion.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.docmgmt.FolderCategory;
import com.nzion.repository.common.impl.HibernateCommonCrudRepository;
import com.nzion.service.CategoryService;

@Service(value = "categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired(required = true)
	private HibernateCommonCrudRepository categoryRepository;

	@Override
	public void addCategory(FolderCategory category) {
	categoryRepository.save(category);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FolderCategory> getChildCategories(FolderCategory category) {
	List<FolderCategory> categories = new ArrayList<FolderCategory>(0);
	if (category == null) {
		categories = categoryRepository.getHibernateTemplate().executeFind(new HibernateCallback() {
			public List<FolderCategory> doInHibernate(Session session) throws HibernateException, SQLException {
			Criteria c = session.createCriteria(FolderCategory.class).add(Restrictions.isNull("parent"));
			return (List<FolderCategory>) c.list();
			}
		});

		if (categories.size() == 0) {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("folder.properties");
			Properties prop = new Properties();
			try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Collection<Object> values = prop.values();
			for (Object obj : values) {
				FolderCategory cat = new FolderCategory();
				cat.setName(obj.toString());
				categoryRepository.save(cat);
				categories.add(cat);
			}
		}
	} else {
		FolderCategory parent = new FolderCategory();
		parent.setParent(category.getParent());
		categories = categoryRepository.simulateExampleSearch(new String[] { "parent" }, parent);
	}
	return categories;
	}

}
