/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author 2020
 */
public class usuario {
    
   
    baseDeDatos bd = new baseDeDatos();
    
    public void ingresar(String user, String pass) throws ParseException{
        
        String validar = new String(bd.consultar("SELECT * FROM \"USUARIO\" WHERE email = '"+user+"'",6,11));
        String creacion = new String(bd.consultar("SELECT * FROM \"USUARIO\" WHERE email = '"+user+"'",11,11));
        String[] partes = creacion.split("-");  
        String actual = this.hoy(); 
        String[] act = actual.split("/");
        Date date1 = new Date(Integer.parseInt(partes[0])-1900,Integer.parseInt(partes[1]),Integer.parseInt(partes[2]));
        Date date2 = new Date(Integer.parseInt(act[0])-1900,Integer.parseInt(act[1]),Integer.parseInt(act[2]));
        
        if(pass.equals(validar)){
            if(864000000 > (date2.getTime()-date1.getTime())){
                
            
                String rol = new String(bd.consultar("SELECT * FROM \"USUARIO\" WHERE email = '"+user+"'",3,12));
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
            }else {
                String id = bd.consultar("SELECT * FROM \"USUARIO\"WHERE email = '"+user+"'", 0, 0);
                JOptionPane.showMessageDialog(null, "contraseña vencida");
                String nueva = JOptionPane.showInputDialog("Ingrese nueva contraseña");
                bd.insertar("UPDATE \"USUARIO\" SET contraseña = "+nueva+" WHERE documento ="+ id );
                bd.insertar("UPDATE \"USUARIO\" SET fechac = '"+this.hoy()+"' WHERE documento ="+ id );
                GUI ventana = new GUI();
                ventana.setVisible(true);
            }
        }else JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
        
    }
    
    public void registrarse(int ID, String nombre, String apellido, String rol, String email, String celular, String contraseña, String tipoDocumento, String direccion, int NIT){
        bd.insertar("INSERT INTO public.\"USUARIO\"(" +
        "documento, nombres, apellidos, rol, email, celular, \"contraseña\", \"tipoDocumento\", fechaIngreso, direccion, \"nitRestaurante\", fechac)" +
        "VALUES ("+ID+",'"+nombre+"','"+apellido+ "'"
        + ",'"+rol+"','"+email+"','"+celular+"'"
        + ", '"+contraseña+"', '"+tipoDocumento+"', '"+this.hoy()+"'"
        + ",'"+direccion+"', "+NIT+",'"+this.hoy()+"')");
        JOptionPane.showMessageDialog(null, "Usuario creado con exito");
    }
    
    public void editar(){
        String usuario = JOptionPane.showInputDialog("Escriba su documento");
        String campo = JOptionPane.showInputDialog("Escriba el campo que desea modificar");
        String valor = JOptionPane.showInputDialog("Escriba el nuevo dato");
        bd.insertar("UPDATE public.\"USUARIO\"" + " SET "+campo+"="+" '"+valor+"'" + "WHERE documento = " +usuario);
        JOptionPane.showMessageDialog(null, "Usuario modificado con exito");
    }
    
    public static String hoy(){
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("YYYY/MM/dd");
        return formato.format(fecha);
    }
    
    public static String dia(){
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("EEEE");
        return formato.format(fecha);
    }    
}
