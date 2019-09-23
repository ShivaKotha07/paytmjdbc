package com.paytm.model;

import java.util.HashMap;

public class Account {
	public Account(String name, String phno, String pwd, String email,float bal) {
		super();
		this.name = name;
		this.phno = phno;
		this.pwd = pwd;
		this.email = email;
		this.bal=bal;
		
	}
	
	private String name;
	private String phno;
	private String pwd;
	private String email;
	private float bal;
	private HashMap<Float, String> transaction = new HashMap<Float, String>();
	public HashMap<Float, String>  gettransaction() {
	    return transaction;
	  }
	public void settransaction(HashMap<Float, String> transaction) {
		  if (transaction == null) {
		    throw new NullPointerException("transaction must not be null");
		  }
		  this.transaction = transaction;
		}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhno() {
		return phno;
	}
	public void setPhno(String phno) {
		this.phno = phno;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Account [ sname=" + name + ", phno=" + phno + ", pwd=" + pwd + ", email=" + email + ", bal=" + bal + "]";
	}
	
	
	public float getBal() {
		return bal;
	}
	public void setBal(float bal) {
		this.bal = bal;
	}


}
