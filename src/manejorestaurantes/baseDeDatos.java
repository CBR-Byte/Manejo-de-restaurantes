/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author Cristhian Botero - Juan Camilo Obando - Santiago Zuñiga 
 */
public class baseDeDatos {
    
    Connection conexion;
    
    
   public void crearConexion(){
         conexion=null;
        try{
            
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Restaurantes", "postgres", "78945612301.cbr");
            
            
        }catch (Exception e){
          System.out.println( "No se pudo crear la conexion" );
          return ;
        }
   }
        
        
    public void insertar(String query){
 
        crearConexion();
        System.out.println(query);

         try{
           Statement s = conexion.createStatement();
           s.executeUpdate(query);
         }
         catch (Exception e){
           e.toString();
         }
          cerrarConexion();
    }
    

    public String consultar(String query,int k,int columnas,int c){

      crearConexion();
      System.out.println(query);
      Object[] fila = new Object[12];

      try{

        Statement s = conexion.createStatement();
        ResultSet rs = s.executeQuery(query);
        if(c!=0){
            for(int i= c;i>0;i--){
                return rs.getObject(i).toString();
            }
        }
        while (rs.next())
        {


          for (int i=0;i<=columnas;i++){
           fila[i] = rs.getObject(i+1);
          }
        }
        
        conexion.close();
      }
        catch (Exception e){
          e.toString();
        }
        
        return fila[k].toString();
    }

    public void cerrarConexion(){

        try{
           conexion.close();
        }
        catch (Exception e){
         e.toString();
        }
    }
}

