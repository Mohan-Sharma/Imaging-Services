package com.nzion.web;

import com.nzion.domain.Location;
import com.nzion.util.Infrastructure;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nzion.domain.Practice;
import com.nzion.util.UserLoginUserDetailsAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sandeep Prusty Apr 5, 2010
 */
public class HibernateTenantSupportFilter extends OpenSessionInViewFilter {

    @Override
    protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        //session.setFlushMode(getFlushMode());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Location location = null;
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            session.enableFilter("DateFilterDef");
            session.enableFilter("EnabledFilter");
            session.enableFilter("ICD9Filter");
            location = Infrastructure.getSelectedLocation();
            if (location != null) {
                session.enableFilter("LocationFilter").setParameter("locationId", location.getId());
            }
        }
        return session;
    }

}