package org.test.zk.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;


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

    public boolean loadConfig( String strConfigPath, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            File configFilePath = new File( strConfigPath );
            
            if ( configFilePath.exists() ) {

                Properties configsData = new Properties();
                
                FileInputStream inputStream = new FileInputStream( configFilePath );
                
                configsData.loadFromXML( inputStream );
                
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Readed config values from file [%s]" ,  strConfigPath ) );

                this.Driver = (String) configsData.get( "driver" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "driver",  this.Driver ) );
                this.Prefix = (String) configsData.get( "prefix" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "prefix",  this.Prefix ) );
                this.Host = (String) configsData.get( "host" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "host",  this.Host ) );
                this.Port = (String) configsData.get( "port" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "port", this.Port ) );
                this.Database = (String) configsData.get( "database" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "database",  this.Database ) );
                this.User = (String) configsData.get( "user" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "user",  this.User ) );
                this.Password = (String) configsData.get( "password" );
                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded value for [%s] [%s]", "password",  this.Password ) );
                
                inputStream.close();                
                
                bResult = true;
                
            }
            else if ( localLogger != null ) {
                //contara los errores hasta el 1021
                localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Config file in path [%s] not found" ,  strConfigPath ) );
                
            }
            
        }
        catch ( Exception Ex ) {
            
            if ( localLogger != null ) {
                //contara las excepciones desde el 1022 
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }
        
        return bResult;
        
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
