package org.test.zk.database;

import java.io.Serializable;

public class DBconfig implements Serializable {

	private static final long serialVersionUID = -4720891613909839360L;

	protected String Driver =null;
	protected String Prefix=null;
	protected String Host=null;
	protected String Port=null;
	protected String Database=null;
	protected String User=null;
	protected String Password=null;
	
	public DBconfig(String lDriver,String lPrefix,String lHost,String lPort,String lDatabase,String lUser,String lPassword){
		this.Driver=lDriver;
		this.Prefix=lPrefix;
		this.Host=lHost;
		this.Port=lPort;
		this.Database=lDatabase;
		this.User=lUser;
		this.Password=lPassword;
	}
	
	public DBconfig(){
		
	}

	public boolean loadconfig(String runningPatch){// este metodo es el que permite ler el archivo de los datos de la connec
		boolean result=false;
		
		return result;
		
	}
	
	public String getDriver() {
		return Driver;
	}

	public void setDriver(String driver) {
		Driver = driver;
	}

	public String getPrefix() {
		return Prefix;
	}

	public void setPrefix(String prefix) {
		Prefix = prefix;
	}

	public String getHost() {
		return Host;
	}

	public void setHost(String host) {
		Host = host;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}

	public String getDatabase() {
		return Database;
	}

	public void setDatabase(String database) {
		Database = database;
	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
}
