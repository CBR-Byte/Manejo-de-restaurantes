/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejorestaurantes;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import java.io.FileNotFoundException;



public class EncargadoVentas extends usuario {
    
    
        
    public void RegistrarVenta(String id, int cantidad , String nombre, int documento, String telefono, int nit) throws FileNotFoundException, DocumentException {
        
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
        String factura = "Select COUNT(*) FROM \"VENTA\"";
        int cod = Integer.parseInt(bd.consultar(factura, 0, 0,0));
        int newCod = cod+1;
        int rows = Integer.parseInt(bd.consultar(c,0,0,0));
        
        for(int i = 0; i<rows;i++){
            exis.add(bd.consultar(existencias,0,i,0));
            req.add(bd.consultar(requerido,0,i,0));
            ins.add(bd.consultar(insumos, 0, i,0));
        }
        for(int i = 0; i < rows;i++){
            if(Integer.parseInt(exis.get(i))< Integer.parseInt(req.get(i))*cantidad){
                flag+=1;
                break;
            }
        }
        if(flag < 1){
           double costo = Double.parseDouble(bd.consultar("SELECT valor FROM public.\"PRODUCTO\" Where idproducto ="+id,0,0,0));
           bd.insertar("INSERT INTO public.\"CLIENTE\"(documento, \"nombreCliente\", telefono)"+ "VALUES ("+documento+",'"+nombre+"', "+telefono+");");
           for(int i = 0; i < rows;i++){
               int nuevasExistencias = Integer.parseInt(exis.get(i))-Integer.parseInt(req.get(i))*cantidad;
               bd.insertar("UPDATE public.\"INSUMOS\" SET existencias="+nuevasExistencias+" WHERE idinsumo="+ins.get(i)+" ;");
            }
           bd.insertar("INSERT INTO public.\"VENTA\"(\"codFactura\", \"montofinal\", fecha, dia, \"nitrestaurante\", \"documentoCliente\")VALUES "
                   + "("+newCod+","+costo*cantidad+", '"+this.hoy()+"'"+ ",'"+this.dia()+"',"+nit+","+documento+");");
           bd.insertar("INSERT INTO public.\"VENTAPRODUCTO\"(\"codFactura\", \"idproducto\", cantidad)VALUES "
                   + "("+newCod+","+id+","+cantidad+");");
           
           String producto = bd.consultar("SELECT nombre FROM public.\"PRODUCTO\" Where idproducto ="+id,0,0,0);

            Document doc = new Document();
            FileOutputStream ficheroPdf = new FileOutputStream("factura_"+cod+".pdf");
            PdfWriter.getInstance(doc,ficheroPdf).setInitialLeading(20);
            doc.open();
            doc.add(new Paragraph("Fecha: "+this.hoy()));
            doc.add(new Paragraph("Cliente: "+nombre));
            doc.add(new Paragraph("Restaurante: "+nit));
            doc.add(new Paragraph("Producto: "+producto));
            doc.add(new Paragraph("Cantidad: "+cantidad));
            doc.add(new Paragraph("Costo total: "+costo*cantidad));
            doc.close();
            
            JOptionPane.showMessageDialog(null, "Factura creada");
            
        }else{
            JOptionPane.showMessageDialog(null, "No hay suficientes insumos para preparar el pedido");
        }
    }
}
    