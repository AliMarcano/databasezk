package org.test.zk.database;

import java.io.Serializable;
import java.sql.*; 
public class CDatabaseConnection implements Serializable {
    
    private static final long serialVersionUID = -821612696326102519L;
    
   // protected final String db_url="jdbc:mysql://127.0.0.1:3306/datos";    
    
   // protected final String user="root";
    
  //  protected final String password="lolisparatodos";
    
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
    }
    
}
