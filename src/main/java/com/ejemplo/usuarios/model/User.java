package com.ejemplo.usuarios.model;

public class User {
	
	private int id;
	private String username;
	private String pass;
	private int rango;
	
	public User(int id, String username, String pass, int rango) {
		super();
		this.id = id;
		this.username = username;
		this.pass = pass;
		this.rango = rango;
	}
	
	public User(String username, String pass, int rango) {
		super();
		this.username = username;
		this.pass = pass;
		this.rango = rango;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getRango() {
		return rango;
	}
	public void setRango(int rango) {
		this.rango = rango;
	}
	
}
