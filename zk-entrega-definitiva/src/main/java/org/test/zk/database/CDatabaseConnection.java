package org.test.zk.database;

import java.io.Serializable;
import java.sql.*;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger; 
public class CDatabaseConnection implements Serializable {
    
    private static final long serialVersionUID = -821612696326102519L;
    
   // protected final String db_url="jdbc:mysql://127.0.0.1:3306/datos";    
    
   // protected final String user="root";
    
  //  protected final String password="lolisparatodos";
    /*
    protected Connection dbConnection = null;
    
    public Connection getDbConection() {
        return dbConnection;
    }

    public void setDbConection(Connection dbConection) {
        this.dbConnection = dbConection;
    }

    public boolean makeConectionToDatabase(DBconfig conf){
        boolean resultado = false;
        try{            
        	if(conf != null){
        		
                Class.forName(conf.getDriver()).newInstance();//Se inicializa el driver de mysql            
                final String url = conf.getPrefix()+conf.getHost()+":"+conf.getPort()+"/"+conf.getDatabase();
                dbConnection=DriverManager.getConnection(url,conf.getUser(),conf.getPassword());//Se le asigna la base de datos con usuario y contrase�a            
                dbConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                dbConnection.setAutoCommit(false);
                resultado=true;	
        	}
        	
        	
        }catch(Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public boolean CloseConnectionToDatabase(){
        boolean resultado = false;
        try{
            if(dbConnection!=null){
                dbConnection.close();
                dbConnection=null;
                resultado=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }*/
    

    protected Connection dbConnection; 
    
    protected DBconfig dbConnectionConfig;
    
    public CDatabaseConnection() {
        
        dbConnection = null;
        
        dbConnectionConfig = null;
        
    }
    
    public Connection getDBConnection() {
        
        return dbConnection;
        
    }

    public void setDBConnection( Connection dbConnection, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection manually" ) );
        
        this.dbConnection = dbConnection;
        
    }
    
    public DBconfig getDBConnectionConfig() {
        
        return dbConnectionConfig;
        
    }

    public void setDBConnectionConfig( DBconfig dbConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage  ) {
        
        localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "Set database connection config manually" ) );

        this.dbConnectionConfig = dbConnectionConfig;
        
    }
    
    public boolean makeConnectionToDB( DBconfig localDBConnectionConfig, CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            if ( this.dbConnection == null ) {

                Class.forName( localDBConnectionConfig.Driver );

                localLogger.logMessage( "1" , CLanguage.translateIf( localLanguage, "Loaded driver [%s]", localDBConnectionConfig.Driver ) );
                
                String strDatabaseURL = localDBConnectionConfig.Prefix + localDBConnectionConfig.Host + ":" + localDBConnectionConfig.Port + "/" + localDBConnectionConfig.Database;

                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Try to connecting to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password ) );

                Connection localDBConnection = DriverManager.getConnection( strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password );
                //DBConnection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/" + strDatabase, strDBUserName, strDBPassword );

                
                localDBConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                
                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connected to [%s] user [%s] password [%s]", strDatabaseURL, localDBConnectionConfig.User, localDBConnectionConfig.Password ) );

                bResult = localDBConnection != null && localDBConnection.isValid( 5 );
                
                if ( bResult ) {
                 
                    localDBConnection.setAutoCommit( false );
                    
                 
                    
                    localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection auto commit set to false" ) );

                    this.dbConnection = localDBConnection; //Save the database connection to this instance object
                    
                    this.dbConnectionConfig = localDBConnectionConfig; //Save the config for the connection to this instance object

                    localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Connection checked" ) );
                    
                }    
                else {
                
                    localDBConnection.close();
                    
                    localDBConnection = null;

                    localLogger.logError( "-1001" , CLanguage.translateIf( localLanguage, "Failed check the connection" ) );
                
                }   

            }
            else {
                
                localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The database is already initiated" ) );
                
            }
            
        }
        catch ( Exception Ex ) {

            if ( localLogger != null ) {
                //crea una entrada
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }       
        
        return bResult;
        
    }
    
    public boolean closeConnectionToDB( CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {

            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Trying to close the connection to the database" ) );

            if ( dbConnection != null ) {
                
                dbConnection.close();
                
                localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Database connection closed successfully" ) );
                
            }
            else {
                
                localLogger.logWarning( "-1" , CLanguage.translateIf( localLanguage, "The connection variable is null" ) );
                
            }

            dbConnection = null;
            dbConnectionConfig = null;
            
            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Set to null connection and conection config variable" ) );

            bResult = true;
        
        }
        catch ( Exception Ex ) {

            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }

        }       

        return bResult;
            
    }
    
    public boolean isValid( CExtendedLogger localLogger, CLanguage localLanguage ) {
        
        boolean bResult = false;
        
        try {
            
            localLogger.logMessage( "1", CLanguage.translateIf( localLanguage, "Checking for database connection is valid" ) );
            
            bResult = dbConnection.isValid( 5 ); //wait max for 5 seconds
            
        } 
        catch ( Exception Ex ) {
            
            if ( localLogger != null ) {
                
                localLogger.logException( "-1021" , Ex.getMessage(), Ex );
                
            }
            
        }
        
        return bResult;
        
}
    
}
