package org.test.zk.database.datamodel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


public class TBLOperator extends CAuditableDataModel implements Serializable{


	private static final long serialVersionUID = -460145854236713435L;
	protected String id;
	protected String name;
	protected String password;
	protected String coment;
	//protected String createby;
	//protected LocalDate createdate ;
	//protected LocalTime createtime ;
	//protected String updateby;
	//protected LocalDate updatedate;
	//protected LocalTime updatetime;
	protected String disableby;
	protected LocalDate disabledate ;
	protected LocalTime disabletime ;
	protected LocalDate lastlogindate ;
	protected LocalTime lastlogintime ;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public String getDisableby() {
		return disableby;
	}
	public void setDisableby(String disableby) {
		this.disableby = disableby;
	}
	public LocalDate getDisabledate() {
		return disabledate;
	}
	public void setDisabledate(LocalDate disabledate) {
		this.disabledate = disabledate;
	}
	public LocalTime getDisabletime() {
		return disabletime;
	}
	public void setDisabletime(LocalTime disabletime) {
		this.disabletime = disabletime;
	}
	public LocalDate getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(LocalDate lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	public LocalTime getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(LocalTime lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	
}
