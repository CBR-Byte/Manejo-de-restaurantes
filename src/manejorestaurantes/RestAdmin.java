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
                    "\"idProducto\", nombre, valor)" +
                    "VALUES ("+ID+",'"+nombre+"',"+valor+");");
        JOptionPane.showMessageDialog(null, "Producto registrado con exito");
    }
    
    public void registrarInsumo(int ID,String nombre, int existencias, double valor){
        bd.insertar("INSERT INTO public.\"INSUMOS\"(" +
                    "\"idInsumo\", nombre,existencias, costo)" +
                    "VALUES ("+ID+",'"+nombre+"',"+existencias+","+valor+");");
        JOptionPane.showMessageDialog(null, "Producto registrado con exito");
    }
}
