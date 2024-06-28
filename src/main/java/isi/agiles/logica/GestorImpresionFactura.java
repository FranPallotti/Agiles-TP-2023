package isi.agiles.logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;

import isi.agiles.dto.LicenciaDTO;
import isi.agiles.dto.TitularDTO;
import javafx.util.Pair;

public class GestorImpresionFactura extends GestorImpresion{
    private final String templatePath = "/isi/agiles/pdf/plantilla-factura.pdf";
    private final String outputName = "factura.pdf";

    /*Ubicaciones y tamaños de los cuadros de texto que se llenaran con datos
     * Nota: Las unidades estan en pixeles. iText no trabaja con unidad de pixeles sino
     * una cierta "user unit". Existe un metodo en la superclase que hace la conversion de
     * coordenada y tamaño en pixeles a user units.
    */
    private final Rectangle hoyDiaRectPx = new Rectangle(1750,620,130,107);
    private final Rectangle hoyMesRectPx = new Rectangle(1950,620,130,107);
    private final Rectangle hoyAnioRectPx = new Rectangle(2150,620,130,107);
    private final Rectangle seniorRectPx = new Rectangle(331,950,2069,75);
    private final Rectangle direccionEncabRectPx = new Rectangle(331,1070,1189,75);
    private final Rectangle claseRectPx = new Rectangle(455,1722,784,60);
    private final Rectangle inicioVigRectPx = new Rectangle(705,1838,784,60);
    private final Rectangle finVigRectPx = new Rectangle(664,1953,784,60);
    private final Rectangle titularRectPx = new Rectangle(465,2069,784,60);
    private final Rectangle docRectPx = new Rectangle(827,2184,784,60);
    private final Rectangle sexoRectPx = new Rectangle(448,2304,784,60);
    private final Rectangle direccRectPx = new Rectangle(543,2417,784,60);
    private final Rectangle grupoFactorRectPx = new Rectangle(650,2530,784,60);
    private final Rectangle esDonanteRectPx = new Rectangle(589,2649,784,60);
    private final Rectangle precioUnitRectPx = new Rectangle(1775,1600,266,60);
    private final Rectangle importeUnitRectPx = new Rectangle(2121,1600,266,60);
    private final Rectangle precioTotalRectPx = new Rectangle(2128,3191,266,60);

    public File imprimirFactura(LicenciaDTO licencia)
    throws FileNotFoundException, IOException, URISyntaxException{
        Pair<PdfDocument,File> pdfAndFile = this.crearPdfDesdePlantilla(templatePath, outputName);
        try(PdfDocument facturaPdf = pdfAndFile.getKey()){
            imprimirEncabezadoFactura(facturaPdf, licencia);
            imprimirDescripcionFactura(facturaPdf, licencia);
        }
        return pdfAndFile.getValue();
    }

    private void imprimirEncabezadoFactura(PdfDocument pdf, LicenciaDTO licencia)
    throws IOException{
        HashMap<Rectangle,String> map = mapearDatosEncabezado(licencia);
        PdfFont fuente = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.imprimirDatos(pdf.getFirstPage(),map,fuente,16);
    }

    private void imprimirDescripcionFactura(PdfDocument pdf, LicenciaDTO licencia)
    throws IOException{
        HashMap<Rectangle,String> map = mapearDatosDescripcion(licencia);
        PdfFont fuente = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        this.imprimirDatos(pdf.getFirstPage(),map,fuente,12);
    }

    private HashMap<Rectangle,String> mapearDatosDescripcion(LicenciaDTO l){
        HashMap<Rectangle,String> map = new HashMap<>();
        TitularDTO t = l.getTitular();
        DateTimeFormatter patronFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder apeNomBuilder = new StringBuilder();
        String apeNom = apeNomBuilder.
                        append(t.getApellido()).
                        append(", ").
                        append(t.getNombre()).
                        toString();
        StringBuilder docBuilder = new StringBuilder();
        String doc = docBuilder.
                    append(t.getNroDoc()).
                    append(" (").
                    append(t.getTipoDoc()).
                    append(")").
                    toString();
        String costo = String.format("%.2f", l.getCosto());
        map.put(claseRectPx,l.getClaseLic().getClase().toString());
        map.put(inicioVigRectPx,l.getInicioVigencia().format(patronFecha));
        map.put(finVigRectPx,l.getFinVigencia().format(patronFecha));
        map.put(titularRectPx,apeNom);
        map.put(docRectPx,doc);
        map.put(sexoRectPx,t.getSexo().toString());
        map.put(direccRectPx,t.getDireccion());
        map.put(grupoFactorRectPx,t.getGrupoSanguineo().toString());
        map.put(esDonanteRectPx,t.getEsDonante() ? "SI" : "NO");
        map.put(precioUnitRectPx,costo);
        map.put(importeUnitRectPx,costo);
        map.put(precioTotalRectPx,costo);
        return map;
    }

    private HashMap<Rectangle,String> mapearDatosEncabezado(LicenciaDTO l){
        HashMap<Rectangle,String> map = new HashMap<>();
        TitularDTO t = l.getTitular();
        LocalDate hoy = LocalDate.now();
        StringBuilder apeNomBuilder = new StringBuilder();
        String apeNom = apeNomBuilder.
                        append(t.getApellido()).
                        append(", ").
                        append(t.getNombre()).
                        toString();
        map.put(hoyDiaRectPx,String.format("%02d",hoy.getDayOfMonth()));
        map.put(hoyMesRectPx,String.format("%02d",hoy.getMonthValue()));
        /*<año>%100 para recuperar solo los ultimos dos digitos
         * Ej: 2024%100 = 24
        */
        map.put(hoyAnioRectPx,String.format("%02d",hoy.getYear()%100));
        map.put(seniorRectPx,apeNom);
        map.put(direccionEncabRectPx,t.getDireccion());
        return map;
    }
}
