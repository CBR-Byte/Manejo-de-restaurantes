/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import java.util.ArrayList;
import javax.swing.JOptionPane;


public class EncargadoVentas extends usuario {
    
    
        
    public void RegistrarVenta(String id, int cantidad , String nombre, int documento, String telefono){
        
        int flag = 0;
        ArrayList<String> exis = new ArrayList<String>();
        ArrayList<String> req = new ArrayList<String>();
        ArrayList<String> ins = new ArrayList<String>();
        String existencias = "SELECT existencias " +
                    "FROM \"INSUMOS\" INNER JOIN \"PRODUCTOINSUMOS\" on \"INSUMOS\".idinsumo = \"PRODUCTOINSUMOS\".idinsumo " +
                    "WHERE idproducto ="+id; 
        String insumos = "SELECT \"INSUMOS\".idinsumo " +
                    "FROM \"INSUMOS\" INNER JOIN \"PRODUCTOINSUMOS\" on \"INSUMOS\".idinsumo = \"PRODUCTOINSUMOS\".idinsumo " +
                    "WHERE idproducto ="+id; 
        String c = "SELECT COUNT(*) FROM  \"INSUMOS\" INNER JOIN \"PRODUCTOINSUMOS\" on \"INSUMOS\".idinsumo = \"PRODUCTOINSUMOS\".idinsumo WHERE idproducto = "+id;
        String requerido = "SELECT cantidadrequerida " +
                    "FROM \"INSUMOS\" INNER JOIN \"PRODUCTOINSUMOS\" on \"INSUMOS\".idinsumo = \"PRODUCTOINSUMOS\".idinsumo " +
                    "WHERE idproducto ="+id;
        
        int rows = Integer.parseInt(bd.consultar(c,0,0));
        for(int i = 0; i<rows;i++){
            exis.add(bd.consultar(existencias,0,i));
            req.add(bd.consultar(requerido,0,i));
            ins.add(bd.consultar(insumos, 0, i));
            }
        for(int i = 0; i < rows;i++){
            if(Integer.parseInt(exis.get(i))< Integer.parseInt(req.get(i))*cantidad){
                flag+=1;
                break;
            }
        }
        if(flag < 1){
            
           // bd.insertar("INSERT INTO public.\"CLIENTE\"(documento, \"nombreCliente\", telefono)"+ "VALUES ("+documento+",'"+nombre+"', "+telefono+");");
           for(int i = 0; i < rows;i++){
               int nuevasExistencias = Integer.parseInt(exis.get(i))-Integer.parseInt(req.get(i))*cantidad;
               bd.insertar("UPDATE public.\"INSUMOS\" SET existencias="+nuevasExistencias+" WHERE idinsumo="+ins.get(i)+" ;");
            } 
        }else{
            JOptionPane.showMessageDialog(null, "No hay suficientes insumos para preparar el pedido");
        }
    }
    
   /* public void RegistrarVenta(String id, int cantidad, String nombre, int documento, String telefono){
        String q = "SELECT existencias " +
                    "FROM \"INSUMOS\" INNER JOIN \"PRODUCTOINSUMOS\" on \"INSUMOS\".idinsumo = \"PRODUCTOINSUMOS\".idinsumo" +
                    "WHERE idproducto = 1	";  
        System.out.println(bd.consultar(q,0,0));
        //if(Integer.parseInt(bd.consultar(q,0,0))>= cantidad){
        //}
    }*/
}
    