package org.test.zk.database;

import java.io.Serializable;
import java.sql.*; 
public class CDatabaseConnection implements Serializable {
    
    private static final long serialVersionUID = -821612696326102519L;
    
    protected final String db_url="jdbc:mysql://127.0.0.1:3306/datos";    
    
    protected final String user="root";
    
    protected final String password="lolisparatodos";
    
    protected Connection dbConnection = null;
    
    public Connection getDbConection() {
        return dbConnection;
    }

    public void setDbConection(Connection dbConection) {
        this.dbConnection = dbConection;
    }

    public boolean makeConectionToDatabase(){
        boolean resultado = false;
        try{            
            Class.forName("com.mysql.jdbc.Driver").newInstance();//Se inicializa el driver de mysql            
            dbConnection=DriverManager.getConnection(db_url,user,password);//Se le asigna la base de datos con usuario y contrase�a            
            dbConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            dbConnection.setAutoCommit(false);
            resultado=true;
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
