package com.altunsoy.todolist.domain.user.payload;

import com.altunsoy.todolist.domain.user.persistence.entity.User;;

public class AuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private User user;
	private boolean isAdmin = false;

	public AuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
