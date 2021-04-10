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

public class CCAdmin extends usuario{
    
   
    public void registrarRestaurante(int NIT, String nombre, String telefono){
        
        bd.insertar("INSERT INTO public.\"RESTAURANTE\"(" +
        "\"NIT\", \"nombreRestaurante\", \"telefonoRestaurante\")" +
        "VALUES ("+NIT+",'"+nombre+"', "+telefono+");");
        JOptionPane.showMessageDialog(null, "Restaurante registrado con exito");
    }
}
