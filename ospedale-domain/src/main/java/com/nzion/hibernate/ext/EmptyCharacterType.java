package com.nzion.hibernate.ext;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.type.CharacterType;

public class EmptyCharacterType extends CharacterType {
	private static final long serialVersionUID = -2923351409120451257L;

	@Override
	public Object get(ResultSet rs, String name) throws SQLException {
	String str = rs.getString(name);
	if (str == null || str.length() == 0) {
		return null;
	} else {
		return new Character(str.charAt(0));
	}
	}
}
