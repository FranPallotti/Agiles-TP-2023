package isi.agiles.logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
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

public class GestorImpresionLicencia extends GestorImpresion {

    private final String templatePath = "/isi/agiles/pdf/plantilla-licencia.pdf";
    private final String outputName = "licencia.pdf";

    /*Ubicaciones y tamaños de los cuadros de texto que se llenaran con datos
     * Nota: Las unidades estan en pixeles. iText no trabaja con unidad de pixeles sino
     * una cierta "user unit". Existe un metodo en la superclase que hace la conversion de
     * coordenada y tamaño en pixeles a user units.
    */
    private final Rectangle nroLicenciaRectPx = new Rectangle(886,603,924,61);
    private final Rectangle apellidoRectPx = new Rectangle(886,743,924,61);
    private final Rectangle nombreRectPx = new Rectangle(886,882,924,61);
    private final Rectangle direccRectPx = new Rectangle(886,1024,1373,61);
    private final Rectangle fechaNacRectPx = new Rectangle(886,1194,670,61);
    private final Rectangle inicioVigRectPx = new Rectangle(886,1326,670,61);
    private final Rectangle finVigRectPx = new Rectangle(1723,1326,527,61);
    private final Rectangle claseRectPx = new Rectangle(1944,603,307,61);
    private final Rectangle esDonanteRectPx = new Rectangle(867,2587,70,30);
    private final Rectangle grupoFactorRectPx = new Rectangle(1542,2587,665,30);
    private final Rectangle observacionesRectangle = new Rectangle(500,2660,1712,30);

    public File imprimirLicencia(LicenciaDTO licencia)
    throws FileNotFoundException,IOException, URISyntaxException{
        Pair<PdfDocument,File> pdfAndFile = this.crearPdfDesdePlantilla(templatePath, outputName);
        try(PdfDocument licenciaPdf = pdfAndFile.getKey()){
            this.imprimirFrenteLicencia(licencia, licenciaPdf);
            this.imprimirReversoLicencia(licencia, licenciaPdf);
        }
        return pdfAndFile.getValue();
    }

    private void imprimirFrenteLicencia(LicenciaDTO licencia, PdfDocument pdf)
    throws IOException{
        HashMap<Rectangle,String> map = mapearDatosFrenteLicencia(licencia);
        PdfFont fuente = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        this.imprimirDatos(pdf.getFirstPage(),map,fuente,16);
    }

    private void imprimirReversoLicencia(LicenciaDTO licencia, PdfDocument pdf)
    throws IOException{
        HashMap<Rectangle,String> map = mapearDatosReversoLicencia(licencia);
        PdfFont fuente = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        this.imprimirDatos(pdf.getFirstPage(),map,fuente,10);
    }

    private HashMap<Rectangle,String> mapearDatosFrenteLicencia(LicenciaDTO l){
        HashMap<Rectangle,String> map = new HashMap<>();
        TitularDTO t = l.getTitular();
        DateTimeFormatter patronFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        map.put(nroLicenciaRectPx, "");
        map.put(apellidoRectPx,t.getApellido().toUpperCase());
        map.put(nombreRectPx,t.getNombre().toUpperCase());
        map.put(direccRectPx, t.getDireccion().toUpperCase());
        map.put(fechaNacRectPx,t.getFechaNacimiento().format(patronFecha));
        map.put(inicioVigRectPx,l.getInicioVigencia().format(patronFecha));
        map.put(finVigRectPx,l.getFinVigencia().format(patronFecha));
        map.put(claseRectPx,l.getClaseLic().getClase().toString());
        return map;
    }

    private HashMap<Rectangle,String> mapearDatosReversoLicencia(LicenciaDTO l){
        HashMap<Rectangle,String> map = new HashMap<>();
        TitularDTO t = l.getTitular();
        String observ = l.getObservaciones();
        map.put(esDonanteRectPx,t.getEsDonante() ? "SI" : "NO");
        map.put(grupoFactorRectPx,t.getGrupoSanguineo().toString());
        if(observ != null && !observ.isBlank()){
            Integer observLen = observ.length();
            map.put(observacionesRectangle, observ.substring(0, observLen > 64 ? 64 : observLen));
        }
        return map;
    }
    
}