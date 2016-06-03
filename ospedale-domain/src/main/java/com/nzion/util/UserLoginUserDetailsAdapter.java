package com.nzion.util;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nzion.domain.UserLogin;

/**
 * @author Sandeep Prusty Apr 6, 2010
 */
public class UserLoginUserDetailsAdapter implements UserDetails {

	private UserLogin userLogin;

	public static final String GRANTED_AUTH_PREFIX = "ROLE_";

	public UserLoginUserDetailsAdapter(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}


	public UserLogin getUserLogin() {
		return userLogin;
	}

	public String getPassword() {
		return userLogin.getPassword();
	}

	public String getUsername() {
		return userLogin.getUsername();
	}

	public boolean isAccountNonExpired() {
		return userLogin.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return userLogin.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return userLogin.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return userLogin.isActive();
	}

	private static final long serialVersionUID = 1L;
}