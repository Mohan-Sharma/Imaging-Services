package com.nzion.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;

/**
 * @author Sandeep Prusty
 *         Apr 16, 2010
 */
public final class Infrastructure implements InitializingBean, ApplicationContextAware {

    public static Map<String, List<Class<?>>> categorizedEntities = new HashMap<String, List<Class<?>>>();

    private static final long serialVersionUID = 1L;

    private static SessionFactory sessionFactory;

    private static ApplicationContext applicationContext;

    private static Location selectedLocation;

    private Infrastructure() {
    }

    private static final Infrastructure infrastructure = new Infrastructure();

    public static Infrastructure getInstance() {
        return infrastructure;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserLogin getUserLogin() {
        Authentication authentication = getAuthentication();
        if (authentication == null || (authentication instanceof AnonymousAuthenticationToken)) return null;
        return ((UserLoginUserDetailsAdapter) authentication.getPrincipal()).getUserLogin();
    }

    public static long getRolesOfLoggedInUser() {
        return getUserLogin().getAuthorization().getRoles();
    }

    public static Person getLoggedInPerson() {
        return getUserLogin().getPerson();
    }

    public static String getUserName() {
        UserLogin login = getUserLogin();
        return login == null ? null : login.getUsername();
    }

    public static SessionFactory getSessionFactory() {
        return Infrastructure.sessionFactory;
    }


    /**
     * @param category Pass PURGEABLE_ENTITIES to retrieve All Purgable Entities
     *                 Pass A..Z to get entity names starting accordingly.
     */
    public static List<Class<?>> getEntityClasses(String category) {
        return categorizedEntities.get(category);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        Infrastructure.sessionFactory = sessionFactory;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Infrastructure.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSpringBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public void afterPropertiesSet() throws Exception {
        for (EntityCategorizer categorizer : EntityCategorizer.CATEGORIZERS)
            categorizer.categorize(categorizedEntities, sessionFactory.getAllClassMetadata().keySet());
    }

    public static Location getSelectedLocation() {
        return selectedLocation;
    }

    public static void setSelectedLocation(Location selectedLocation) {
        Infrastructure.selectedLocation = selectedLocation;
    }

    public static Practice getPractice() {
        List<Practice> results = sessionFactory.getCurrentSession().createQuery("from Practice order by id DESC").list();
        if(results.size() == 0)
        	return null;
        return (Practice) results.get(0);
    }

}