/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import javax.swing.JOptionPane;

/**
 *
 * @author 2020
 */
public class RestAdmin extends usuario{
    
    public void registrarProducto(int ID, String nombre, double valor){
        bd.insertar("INSERT INTO public.\"PRODUCTO\"(" +
                    "\"idproducto\", nombre, valor)" +
                    "VALUES ("+ID+",'"+nombre+"',"+valor+");");
        JOptionPane.showMessageDialog(null, "Producto registrado con exito");
    }
    
    public void registrarInsumo(int ID,String nombre, int existencias, double valor){
        String c =bd.consultar("SELECT COUNT(*) FROM  \"INSUMOS\" WHERE idinsumo ="+ID+"",0,0,0);
        System.out.println(c);
        if(c.equals("0")){
            bd.insertar("INSERT INTO public.\"INSUMOS\"(" +
                    "\"idinsumo\", nombre,existencias, costo)" +
                    "VALUES ("+ID+",'"+nombre+"',"+existencias+","+valor+");");
        JOptionPane.showMessageDialog(null, "Producto registrado con exito");
        }else{
            int viejas = Integer.parseInt(bd.consultar("SELECT existencias FROM \"INSUMOS\" WHERE idinsumo ="+ID+"",0, 0,0));
            int nuevas = viejas+existencias;
            
            bd.insertar("UPDATE \"INSUMOS\" SET existencias = "+nuevas +" WHERE idinsumo ="+ ID );
        }
        
    }
}
