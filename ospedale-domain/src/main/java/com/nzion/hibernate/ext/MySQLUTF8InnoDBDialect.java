package com.nzion.hibernate.ext;

import org.hibernate.dialect.MySQLInnoDBDialect;

public class MySQLUTF8InnoDBDialect extends MySQLInnoDBDialect {

	@Override
	public String getTableTypeString() {
	return " ENGINE = InnoDB AUTO_INCREMENT=10001 DEFAULT CHARACTER SET =utf8 COLLATE =utf8_general_ci ";
	}

}
