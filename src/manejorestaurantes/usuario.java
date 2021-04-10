/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author 2020
 */
public class usuario {
    
   
    baseDeDatos bd = new baseDeDatos();
    
    public void ingresar(String user, String pass){
        
        String validar = new String(bd.consultar("SELECT * FROM \"USUARIO\"WHERE email = '"+user+"'",6,10));
        if(pass.equals(validar)){
            String rol = new String(bd.consultar("SELECT * FROM \"USUARIO\"WHERE email = '"+user+"'",3,10));
            switch (rol) {
                case "CC Admin":
                    JOptionPane.showMessageDialog(null, "Bienvenido señor(a) Administrador del Centro comercial");
                    AdminCC menu = new AdminCC();
                    menu.setVisible(true);
                    break;
                case "Rest Admin":
                    JOptionPane.showMessageDialog(null, "Bienvenido señor(a) administrador del restaurante");
                    AdminRest menu2 = new AdminRest();
                    menu2.setVisible(true);
                    break;
                case "Encargado":
                    JOptionPane.showMessageDialog(null, "Bienvenido señor(a) Encargado del restaurante");
                    Encargado menu3 = new Encargado();
                    menu3.setVisible(true);
                    break;
            }
        }else JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
        
    }
    
    public void registrarse(int ID, String nombre, String apellido, String rol, String email, String celular, String contraseña, String tipoDocumento, String direccion, int NIT){
        bd.insertar("INSERT INTO public.\"USUARIO\"(" +
        "documento, nombres, apellidos, rol, email, celular, \"contraseña\", \"tipoDocumento\", \"fechaIngreso\", direccion, \"nitRestaurante\")" +
        "VALUES ("+ID+",'"+nombre+"','"+apellido+ "'"
        + ",'"+rol+"','"+email+"','"+celular+"'"
        + ", '"+contraseña+"', '"+tipoDocumento+"', '"+this.hoy()+"'"
        + ",'"+direccion+"', "+NIT+");");
        JOptionPane.showMessageDialog(null, "Usuario creado con exito");
    }
    
    public void editar(){
        String usuario = JOptionPane.showInputDialog("Escriba su documento");
        String campo = JOptionPane.showInputDialog("Escriba el campo que desea modificar");
        String valor = JOptionPane.showInputDialog("Escriba el nuevo dato");
        bd.insertar("UPDATE public.\"USUARIO\"" + " SET "+campo+"="+" '"+valor+"'" + "WHERE documento = " +usuario);
        JOptionPane.showMessageDialog(null, "Usuario modificado con exito");
    }
    
    public static String hoy()
    {
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(fecha);
    }
    
}
