package org.test.zk.dao;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.test.zk.database.CDatabaseConnection;
import org.test.zk.datamodel.TBLPerson;

public class TBLPersonDAO {
    public static TBLPerson loadData(final CDatabaseConnection databaseConnection, final String CI) {
        TBLPerson resultado = null;
        try {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {
                Statement statement = databaseConnection.getDbConection().createStatement();
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
            e.printStackTrace();
        }
        return resultado;
    }

    public static boolean deleteData(final CDatabaseConnection databaseConnection, final String CI) {
        boolean bresultado = false;
        final String sqlQuerry = "DELETE FROM persona WHERE idpersona ='"+CI+"'";
        try{
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {
                Statement statement = databaseConnection.getDbConection().createStatement();
                statement.executeUpdate(sqlQuerry);
                bresultado=true;
                databaseConnection.getDbConection().commit();//Se hace el comit
                statement.close();
            }
        }catch(Exception e){
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDbConection().rollback();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return bresultado;
    }

    public static boolean insertData(final CDatabaseConnection databaseConnection, final TBLPerson tblperson) {
        boolean bresultado = false;//Validaci�n
        final String sqlQuerry = "'"+tblperson.getStrci() + "','" + tblperson.getnombre() + "','" + tblperson.getapellido()//Ahorro de tiempo
                + "','"+ tblperson.getGender() + "','" + tblperson.getCumple() + "','"
                + tblperson.getComment() + "','root','" + LocalDate.now().toString() + "','" + LocalTime.now().toString()
                + "',null,null,null";
        try {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDbConection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(//se da la orden de crear una tupla
                        "Insert Into persona(idpersona,nombre,apellido,genero,fecha,comentario,createby,createdate,createtime,updateeby,updatebydate,updatebytime) Values("
                        + sqlQuerry + ")");
                databaseConnection.getDbConection().commit();//Se hace el comit
                bresultado=true;//Se confirma que funcion�
                statement.close();//Se liberan recursos
            }
        } catch (Exception e) {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDbConection().rollback();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return bresultado;//Se da respuesta
    }

    public static boolean updateData(final CDatabaseConnection databaseConnection, final TBLPerson tblperson) {
        boolean bresultado = false;
        final String sqlQuerry = "Update persona Set idpersona='"+tblperson.getStrci()+"',nombre='"+tblperson.getnombre()+"',apellido='"+tblperson.getapellido()+"',genero="+tblperson.getGender()+",fecha='"+tblperson.getCumple()+"',Comentario='"+tblperson.getComment()+"',updateeby='tester',updatebydate='"+LocalDate.now().toString()+"',updatebytime='"+LocalTime.now().toString()+"' Where idpersona ='"+tblperson.getStrci()+"'";
        try {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {//Si se est� conectado a la bd
                Statement statement = databaseConnection.getDbConection().createStatement();//Se crea el statement para comm con mysql
                statement.executeUpdate(sqlQuerry);
                databaseConnection.getDbConection().commit();//Se hace el comit
                bresultado=true;//Se confirma que funcion�
                statement.close();//Se liberan recursos
            }
        } catch (Exception e) {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {//Si se est� conectado a la bd
                try{
                    databaseConnection.getDbConection().rollback();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return bresultado;
    }

    public static List<TBLPerson> searchData(final CDatabaseConnection databaseConnection) {
        List<TBLPerson> resultado = new ArrayList<TBLPerson>();
        try {
            if (databaseConnection != null && databaseConnection.getDbConection() != null) {
                Statement statement = databaseConnection.getDbConection().createStatement();
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
            e.printStackTrace();
        }
        return resultado;
    }
}
