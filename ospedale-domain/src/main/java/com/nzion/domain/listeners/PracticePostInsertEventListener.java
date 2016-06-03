package com.nzion.domain.listeners;

import com.nzion.domain.Practice;
import com.nzion.util.Infrastructure;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.jdbc.util.Formatter;
import org.hibernate.util.JDBCExceptionReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PracticePostInsertEventListener implements PostInsertEventListener {

	private static final long serialVersionUID = 1L;
	
	private final Set<URL> files = new HashSet<URL>();
	
	private static final Logger log = LoggerFactory.getLogger(PracticePostInsertEventListener.class);
	
	private final Formatter formatter = FormatStyle.BASIC.getFormatter();
	
	private static final String delimiter = ";";
	
	String[] fileNames = null;
	
	private DataSource datasource;

	public void onPostInsert(PostInsertEvent event) {

	String entityName = event.getPersister().getEntityName();
	if ("com.nzion.domain.Practice".equalsIgnoreCase(entityName)) {
		datasource = (DataSource) Infrastructure.getSpringBean("dataSource");
		File f = new File(Thread.currentThread().getContextClassLoader().getResource("seed/family_member.sql").getPath());
		File dir = new File(f.getParent());
		files.clear();
		String[] fileNames = dir.list();
		for (String s : fileNames) {
			if (s.contains("cardiology.csv") || s.contains("icdCodeSet.properties")|| s.contains("cptCodeSet.properties") || s.contains("syndromes.xml")) 
				continue;
			try {
				System.out.println(" File name " + s);
				File tmp = new File(dir, s);
				if (!tmp.isDirectory()) {
					System.out.println("Adding File " + s);
					files.add(new File(dir, s).toURI().toURL());
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		Practice p = (Practice) event.getEntity();
		final Long practiceId = p.getId();
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			executeSQL(connection, practiceId);
//			insertIcdCodeSet(p, event);
//			insertCptCodeSet(p, event);
//			insertMedicationCodeSet(p, event);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new HibernateException(e);
        }finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	}

	private void executeSQL(Connection connection, Long practiceId) throws SQLException {
	Statement statement = connection.createStatement();
	try {
		for (URL url : files) {
			InputStream stream = url.openStream();
			try {
				LineIterator it = IOUtils.lineIterator(stream, "UTF-8");
				while (it.hasNext()) {
					String sql = it.nextLine();
					sql = String.format(sql, practiceId);
					System.out.println(sql);
					if (StringUtils.isNotBlank(sql)) insertRecord(connection, statement, sql);
				}
			} finally {
				IOUtils.closeQuietly(stream);
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		statement.close();
	}
	}

	public void insertRecord(Connection connection, Statement statement, String sql) throws SQLException {
	String formatted = formatter.format(sql);
	if (delimiter != null) {
		formatted += delimiter;
	}
        System.out.println(formatted);
	log.debug(formatted);
	statement.execute(sql);
	SQLWarning warnings = statement.getWarnings();
	if (warnings != null) {
		JDBCExceptionReporter.logAndClearWarnings(connection);
	}
	}


}