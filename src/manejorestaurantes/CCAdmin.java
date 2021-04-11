/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
    
    public void generarReportes() throws IOException, DocumentException{
        
        DefaultCategoryDataset grafico = new DefaultCategoryDataset();
        baseDeDatos bd = new baseDeDatos();
        int rows = Integer.parseInt(bd.consultar("Select count(distinct nitrestaurante) FROM \"VENTA\"", 0, 0,0));
        ArrayList nits = new ArrayList();
        ArrayList nombres = new ArrayList();
        for(int i = 0; i<rows;i++){
            nits.add(bd.consultar("Select distinct nitrestaurante From \"VENTA\" ORDER BY nitrestaurante", 0, i,0));
            nombres.add(bd.consultar("Select nombrerestaurante From \"RESTAURANTE\" WHERE nit ="+nits.get(i).toString(), 0, i,0));
            
            
        }
        for(int i = 0;i<nombres.size();i++){
            grafico.setValue(Double.parseDouble(bd.consultar("SELECT AVG(montoFinal) FROM \"VENTA\" WHERE nitrestaurante ="+nits.get(i),0,0,0)),"Vendido" ,nombres.get(i).toString());
        }
        JFreeChart chart = ChartFactory.createBarChart("Promedio de ventas", "Restaurantes", "Vendido en Pesos", grafico);
        BufferedImage image = chart.createBufferedImage(600, 400);
        ImageIO.write(image, "png", new File("promedioVentas.png"));
        JOptionPane.showMessageDialog(null,"Grafico creado");

        Document doc = new Document();
        FileOutputStream ficheroPdf = new FileOutputStream("Total_vendido_por_día.pdf");
        PdfWriter.getInstance(doc,ficheroPdf).setInitialLeading(20);
        doc.open();
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'lunes'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Lunes: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'lunes'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'lunes'", 0, 0, 0))==0){
            doc.add(new Paragraph("Lunes: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'martes'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Martes: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'martes'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'martes'", 0, 0, 0))==0){
            doc.add(new Paragraph("Martes: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'miércoles'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Miércoles: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'miércoles'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'miércoles'", 0, 0, 0))==0){
            doc.add(new Paragraph("Miercoles: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'jueves'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Jueves: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'jueves'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'jueves'", 0, 0, 0))==0){
            doc.add(new Paragraph("Jueves: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'viernes'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Viernes: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'viernes'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'viernes'", 0, 0, 0))==0){
            doc.add(new Paragraph("Viernes: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'sábado'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Sábado: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'sábado'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'sábado'", 0, 0, 0))==0){
            doc.add(new Paragraph("Sábado: 0"));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'domingo'", 0, 0, 0))!=0){
            doc.add(new Paragraph("Domingo: "+ bd.consultar("Select SUM(montofinal) FROM \"VENTA\" WHERE dia = 'domingo'", 0, 0, 0)));
        }
        if(Integer.parseInt(bd.consultar("Select count(*) From \"VENTA\" WHERE dia = 'domingo'", 0, 0, 0))==0){
            doc.add(new Paragraph("Domingo: 0"));
        }
        doc.close();
        JOptionPane.showMessageDialog(null, "Reporte por días creado");
    }
}
