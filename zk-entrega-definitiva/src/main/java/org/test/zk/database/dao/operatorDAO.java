package org.test.zk.database.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.datamodel.TBLOperator;


import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class operatorDAO {

	public static TBLOperator loadData(final CDatabaseConnection databaseConnection, final String id, CExtendedLogger loger,CLanguage lenguaje) {
		TBLOperator resul = null;
		
		 try {
	            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
	                Statement statement = databaseConnection.getDBConnection().createStatement();
	                ResultSet rs = statement.executeQuery("Select * from TBLoperator Where id='"+id+"'");
	                if (rs.next()) {
	                    resul = new TBLOperator();
	                    resul.setId(rs.getString("id"));
	                    resul.setName(rs.getString("name"));
	                    resul.setpassword(rs.getString("password"));
	                   
	                    resul.setDisableby(rs.getString("disableby"));
	                    resul.setDisabledate(rs.getDate("disabledate").toLocalDate());
	                    resul.setDisabletime(rs.getTime("disabletime").toLocalTime());
	                    
	                    resul.setLastlogindate(rs.getDate("lastlogindate") != null? rs.getDate("lastlogindate").toLocalDate() : null);
	                    resul.setLastlogintime(rs.getTime("lastlogintime") != null ? rs.getTime("lastlogintime").toLocalTime() : null);

	                    //------------------
	                    
	                    resul.setCreadoPor(rs.getString("createby"));
	                    resul.setCreadoFecha(rs.getDate("createdate").toLocalDate());
	                    resul.setCreadoHora(rs.getTime("createtime").toLocalTime());
	                    resul.setActualizadoPor(rs.getString("updateeby"));
	                    resul.setActualizadoFecha(rs.getDate("updatebydate") != null? rs.getDate("updatebydate").toLocalDate() : null);
	                    resul.setActualizadoHora(rs.getTime("updatebytime") != null ? rs.getTime("updatebytime").toLocalTime() : null);

	                    
	                    //resultado=tblperson;
	                }
	                rs.close();
	                statement.close();
	                
	                // NO SE CIERRA LA CONEXI�N
	            }
	        } catch (Exception e) {
	            if(loger!=null){
	            	loger.logException("-1021", e.getMessage(),e);
	            	
	            }
	        }
		
		return resul;
		
		
	}
//ss
    public static boolean deleteData(final CDatabaseConnection databaseConnection, final String id, CExtendedLogger loger,CLanguage lenguaje) {
        boolean resul = false;
        final String sqlQuerry = "Delete From TBLoperador Where id ='"+id+"'";
        try{
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
                Statement statement = databaseConnection.getDBConnection().createStatement();
                statement.executeUpdate(sqlQuerry);
                resul=true;
                databaseConnection.getDBConnection().commit();//Se hace el comit
                statement.close();
            }
        }catch(Exception e){
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDBConnection().rollback();
                }catch(Exception ex){
                	if(loger!=null){
                    	loger.logException("-1021", e.getMessage(),e);
                    	
                    }
                }
            }
            if(loger!=null){
            	loger.logException("-1022", e.getMessage(),e);
            	
            }
        }

        return resul;
    }
    
    
    public static boolean insertData(final CDatabaseConnection databaseConnection, final TBLOperator operator, CExtendedLogger loger,CLanguage lenguaje) {
        boolean resul = false;//Validaci�n
        final String sqlQuerry = "'"+operator.getId() + "','" + operator.getName() + "','" + operator.getpassword()//Ahorro de tiempo
        + "','"+ operator.getComent()+ "','test','" + LocalDate.now().toString() + "','" + LocalTime.now().toString()+"'";
       
        final String truesql="Insert Into TBLoperador(id,Name,Password,coment,createby,createdate,createtime) Values("+ sqlQuerry + ")";
        try {
        	if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
        	    Statement statement = databaseConnection.getDBConnection().createStatement();//Se crea el statement para comm con mysql	
             	//si los cambos no estan presentes en la insersion el SQL inserta por defecto null
        	    statement.executeUpdate(truesql);
        		databaseConnection.getDBConnection().commit();//Se hace el comit
        		statement.close();//Se liberan recursos
        		resul=true;//Se confirma que funcion�
        	}
        } catch (Exception e) {
        	if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
        		try{
        			databaseConnection.getDBConnection().rollback();
        		}catch(Exception ex){
        			if(loger!=null){
        				loger.logException("-1021", e.getMessage(),e);
            	
        			}
        		}
        	}
        	if(loger!=null){
        		loger.logException("-1022", e.getMessage(),e);
    	
        	}
        }
        
        
        return resul;//Se da respuesta
    }
    
    public static boolean updateData(final CDatabaseConnection databaseConnection, final TBLOperator operator, CExtendedLogger loger,CLanguage lenguaje) {
        boolean resul = false;
        
        final String disabledate= operator.getDisableby() != null?"'"+LocalDate.now().toString()+"'":"null";
        final String disabletime= operator.getDisableby() != null?"'"+LocalTime.now().toString()+"'":"null";
        final String sqlQuerry = "Update TBLoperador Set id='"+operator.getId()+"',Name='"+operator.getName()+"',Password='"+operator.getpassword()+"',coment='"+operator.getComent()+"',updateby='tester',updatedate='"+LocalDate.now().toString()+"',updatetime='"+LocalTime.now().toString()+"',disableby ='"+operator.getDisableby()+"',disabledate ="+disabledate+", disabletime '"+disabletime+"  Where id ='"+operator.getId()+"'";
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDBConnection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(sqlQuerry);
                databaseConnection.getDBConnection().commit();//Se hace el comit
                resul=true;//Se confirma que funcion�
                statement.close();//Se liberan recursos
            }
        } catch (Exception e) {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDBConnection().rollback();
                }catch(Exception ex){
                	if(loger!=null){
                    	loger.logException("-1021", e.getMessage(),e);
                    	
                    }
                }
            }
            if(loger!=null){
            	loger.logException("-1022", e.getMessage(),e);
            	
            }
        }
        return resul;
    }
    
    public static boolean updatelastlogin(final CDatabaseConnection databaseConnection, final String id, CExtendedLogger loger,CLanguage lenguaje) {
        boolean resul = false;
        final String sqlQuerry = "Update TBLoperador Set lastlogindate ='"+LocalDate.now().toString()+"',lastlogintime='"+LocalTime.now().toString()+" Where id ='"+id+"'";
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDBConnection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(sqlQuerry);
                databaseConnection.getDBConnection().commit();//Se hace el comit
                resul=true;//Se confirma que funcion�
                statement.close();//Se liberan recursos
            }
        } catch (Exception e) {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDBConnection().rollback();
                }catch(Exception ex){
                	if(loger!=null){
                    	loger.logException("-1021", e.getMessage(),e);
                    	
                    }
                }
            }
            if(loger!=null){
            	loger.logException("-1022", e.getMessage(),e);
            	
            }
        }
        return resul;
    }
    
    
    
    
    public static List<TBLOperator> searchData(final CDatabaseConnection databaseConnection, CExtendedLogger loger,CLanguage lenguaje) {
        List<TBLOperator> resul = new ArrayList<TBLOperator>();
        
        
        
        
        return resul;
    }

    public static TBLOperator checkvalid(final CDatabaseConnection databaseConnection, final String name,final String password, CExtendedLogger loger,CLanguage lenguaje){
    	TBLOperator resul=null;
    	
		 try {
	            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
	                Statement statement = databaseConnection.getDBConnection().createStatement();
	                ResultSet rs = statement.executeQuery("Select * from TBLoperador Where Name='"+name+"' and Password = '"+password+"'");
	                if (rs.next()) {
	                    resul = new TBLOperator();
	                    resul.setId(rs.getString("id"));
	                    resul.setName(rs.getString("Name"));
	                    resul.setpassword(rs.getString("Password"));
	                    resul.setComent(rs.getString("coment"));
	                    
	                    resul.setDisableby(rs.getString("disableby"));
	                    resul.setDisabledate(rs.getDate("disabledate") != null? rs.getDate("disabledate").toLocalDate() : null);
	                    resul.setDisabletime(rs.getTime("disabletime") != null? rs.getTime("disabletime").toLocalTime() : null);
	                    
	                    resul.setLastlogindate(rs.getDate("lastlogindate") != null? rs.getDate("lastlogindate").toLocalDate() : null);
	                    resul.setLastlogintime(rs.getTime("lastlogintime") != null ? rs.getTime("lastlogintime").toLocalTime() : null);

	                    //------------------
	                    
	                    resul.setCreadoPor(rs.getString("createby"));
	                    resul.setCreadoFecha(rs.getDate("createdate").toLocalDate());
	                    resul.setCreadoHora(rs.getTime("createtime").toLocalTime());
	                    resul.setActualizadoPor(rs.getString("updateeby"));
	                    resul.setActualizadoFecha(rs.getDate("updatebydate") != null? rs.getDate("updatebydate").toLocalDate() : null);
	                    resul.setActualizadoHora(rs.getTime("updatebytime") != null ? rs.getTime("updatebytime").toLocalTime() : null);

	                    
	                    //resultado=tblperson;
	                }
	                rs.close();
	                statement.close();
	                
	                // NO SE CIERRA LA CONEXI�N
	            }
	        } catch (Exception e) {
	            if(loger!=null){
	            	loger.logException("-1021", e.getMessage(),e);
	            	
	            }
	        }
    	
    	return resul;
    }

}
