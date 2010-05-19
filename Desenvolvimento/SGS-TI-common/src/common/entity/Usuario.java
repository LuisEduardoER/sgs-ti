package common.entity;

import java.io.Serializable;

public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	/**
	* TODO - Documentar
	*/
	public Usuario() {
	}
	
	/**
	* TODO - Descrever melhor os campos
	*/
	/**
	 * 
	 * @param username
	 * @param password
	 */
	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/*
	 * GETTERs AND SETTERs
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	* TODO - Documentar
	*/
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
