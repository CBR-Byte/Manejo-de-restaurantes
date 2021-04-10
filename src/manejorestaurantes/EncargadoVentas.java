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



public class EncargadoVentas extends usuario {
    
    
        
    public void RegistrarVenta(String id, int cantidad , String nombre, int documento, String telefono, int nit) throws IOException{
        
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
        int cod = Integer.parseInt(bd.consultar(factura, 0, 0));
        int newCod = cod+1;
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
           double costo = Double.parseDouble(bd.consultar("SELECT valor FROM public.\"PRODUCTO\" Where idproducto ="+id,0,0));
           bd.insertar("INSERT INTO public.\"CLIENTE\"(documento, \"nombreCliente\", telefono)"+ "VALUES ("+documento+",'"+nombre+"', "+telefono+");");
           for(int i = 0; i < rows;i++){
               int nuevasExistencias = Integer.parseInt(exis.get(i))-Integer.parseInt(req.get(i))*cantidad;
               bd.insertar("UPDATE public.\"INSUMOS\" SET existencias="+nuevasExistencias+" WHERE idinsumo="+ins.get(i)+" ;");
            }
           bd.insertar("INSERT INTO public.\"VENTA\"(\"codFactura\", \"montoFinal\", fecha, dia, \"nitRestaurante\", \"documentoCliente\")VALUES "
                   + "("+newCod+","+costo*cantidad+", '"+this.hoy()+"'"+ ",'"+this.dia()+"',"+nit+","+documento+");");
           bd.insertar("INSERT INTO public.\"VENTAPRODUCTO\"(\"codFactura\", \"idProducto\", cantidad)VALUES "
                   + "("+newCod+","+id+","+cantidad+");");
           
           String producto = bd.consultar("SELECT nombre FROM public.\"PRODUCTO\" Where idproducto ="+id,0,0);
           PDDocument document = new PDDocument();
           PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Text
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ITALIC, 5);
            contentStream.newLineAtOffset( 20, page.getMediaBox().getHeight() - 52);
            contentStream.showText("Fecha: "+this.hoy()+
                    " Cliente: "+nombre+
                    " Restaurante:"+nit+
                    " Producto: "+producto+
                    " Cantidad: "+cantidad+
                    " Costo: "+costo*cantidad);
            contentStream.endText();
            contentStream.close();
            document.save("factura_"+cod+".pdf");
            JOptionPane.showMessageDialog(null, "Factura creada");
            
        }else{
            JOptionPane.showMessageDialog(null, "No hay suficientes insumos para preparar el pedido");
        }
    }
}
    