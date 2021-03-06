package org.test.zk.database.dao;


import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.test.zk.database.CDatabaseConnection;
import org.test.zk.database.datamodel.TBLPerson;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class PersonDAO {
    public static TBLPerson loadData(final CDatabaseConnection databaseConnection, final String CI, CExtendedLogger loger,CLanguage lenguaje) {
        TBLPerson resultado = null;
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
                Statement statement = databaseConnection.getDBConnection().createStatement();
                ResultSet rs = statement.executeQuery("Select * from persona Where idpersona='"+CI+"'");
                if (rs.next()) {
                    resultado = new TBLPerson();
                    resultado.setci(rs.getString("idpersona"));
                    resultado.setnombre(rs.getString("Nombre"));
                    resultado.setapellido(rs.getString("Apellido"));
                   
                    resultado.setGender(rs.getInt("Genero"));
                    resultado.setCumple(rs.getDate("fecha").toLocalDate());
                    resultado.setComment((rs.getString("Comentario")));
                    resultado.setCreadoPor(rs.getString("createby"));
                    resultado.setCreadoFecha(rs.getDate("createdate").toLocalDate());
                    resultado.setCreadoHora(rs.getTime("createtime").toLocalTime());
                    resultado.setActualizadoPor(rs.getString("updateeby"));
                    resultado.setActualizadoFecha(rs.getDate("updatebydate") != null? rs.getDate("updatebydate").toLocalDate() : null);
                    resultado.setActualizadoHora(rs.getTime("updatebytime") != null ? rs.getTime("updatebytime").toLocalTime() : null);
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
        return resultado;
    }

    public static boolean deleteData(final CDatabaseConnection databaseConnection, final String CI, CExtendedLogger loger,CLanguage lenguaje) {
        boolean bresultado = false;
        final String sqlQuerry = "Delete From persona Where idpersona ='"+CI+"'";
        try{
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
                Statement statement = databaseConnection.getDBConnection().createStatement();
                statement.executeUpdate(sqlQuerry);
                bresultado=true;
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
        return bresultado;
    }

    public static boolean insertData(final CDatabaseConnection databaseConnection, final TBLPerson tblperson, CExtendedLogger loger,CLanguage lenguaje) {
        boolean bresultado = false;//Validaci�n
        final String sqlQuerry = "'"+tblperson.getStrci() + "','" + tblperson.getnombre() + "','" + tblperson.getapellido()//Ahorro de tiempo
                + "','"+ tblperson.getGender() + "','" + tblperson.getCumple() + "','"
                + tblperson.getComment() + "','root','" + LocalDate.now().toString() + "','" + LocalTime.now().toString()
                + "',null,null,null";
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDBConnection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(//se da la orden de crear una tupla
                        "Insert Into persona(idpersona,nombre,apellido,genero,fecha,comentario,createby,createdate,createtime,updateeby,updatebydate,updatebytime) Values("
                        + sqlQuerry + ")");
                databaseConnection.getDBConnection().commit();//Se hace el comit
                bresultado=true;//Se confirma que funcion�
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
        return bresultado;//Se da respuesta
    }

    public static boolean updateData(final CDatabaseConnection databaseConnection, final TBLPerson tblperson, CExtendedLogger loger,CLanguage lenguaje) {
        boolean bresultado = false;
        final String sqlQuerry = "Update persona Set idpersona='"+tblperson.getStrci()+"',nombre='"+tblperson.getnombre()+"',apellido='"+tblperson.getapellido()+"',genero="+tblperson.getGender()+",fecha='"+tblperson.getCumple()+"',comentario='"+tblperson.getComment()+"',updateeby='tester',updatebydate='"+LocalDate.now().toString()+"',updatebytime='"+LocalTime.now().toString()+"' Where idpersona ='"+tblperson.getStrci()+"'";
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDBConnection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(sqlQuerry);
                databaseConnection.getDBConnection().commit();//Se hace el comit
                bresultado=true;//Se confirma que funcion�
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
        return bresultado;
    }

    public static List<TBLPerson> searchData(final CDatabaseConnection databaseConnection, CExtendedLogger loger,CLanguage lenguaje) {
        List<TBLPerson> resultado = new ArrayList<TBLPerson>();
        try {
            if (databaseConnection != null && databaseConnection.getDBConnection() != null) {
                Statement statement = databaseConnection.getDBConnection().createStatement();
                ResultSet rs = statement.executeQuery("Select * from persona");
                while (rs.next()) {
                    TBLPerson tblperson = new TBLPerson();
                    tblperson.setci(rs.getString("idpersona"));
                    tblperson.setnombre(rs.getString("nombre"));
                    tblperson.setapellido(rs.getString("apellido"));
                 
                    tblperson.setGender(rs.getInt("genero"));
                    tblperson.setCumple(rs.getDate("fecha").toLocalDate());
                    tblperson.setComment((rs.getString("Comentario")));
                    tblperson.setCreadoPor(rs.getString("createby"));
                    tblperson.setCreadoFecha(rs.getDate("createdate").toLocalDate());
                    tblperson.setCreadoHora(rs.getTime("createtime").toLocalTime());
                    tblperson.setActualizadoPor(rs.getString("updateeby"));
                    tblperson.setActualizadoFecha(rs.getDate("updatebydate") != null
                            ? rs.getDate("updatebydate").toLocalDate() : null);
                    tblperson.setActualizadoHora(
                            rs.getTime("updatebytime") != null ? rs.getTime("updatebytime").toLocalTime() : null);
                    resultado.add(tblperson);
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
        return resultado;
    }
}
