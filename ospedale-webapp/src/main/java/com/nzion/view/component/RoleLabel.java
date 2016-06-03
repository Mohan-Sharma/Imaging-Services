/**
 * 
 */
package com.nzion.view.component;

import org.zkoss.zul.Label;

import com.nzion.domain.Roles;

/**
 * @author Nafis
 *
 */
public class RoleLabel extends Label{

	public void setObject(Long role) {
	if(role != null)
		setValue( Roles.getRoleName(role));
	}
	private static final long serialVersionUID = 1L;
}
