package isi.agiles.logica;

import java.io.File;
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
    private final String outputName = "output-licencia.pdf";

    private final Rectangle nroLicenciaRectanglePx = new Rectangle(886,603,924,61);
    private final Rectangle apellidoRectanglePx = new Rectangle(886,743,924,61);
    private final Rectangle nombreRectanglePx = new Rectangle(886,882,924,61);
    private final Rectangle direccRectanglePx = new Rectangle(886,1024,1373,61);
    private final Rectangle fechaNacRectanglePx = new Rectangle(886,1194,670,61);
    private final Rectangle inicioVigRectanglePx = new Rectangle(886,1326,670,61);
    private final Rectangle finVigRectanglePx = new Rectangle(1723,1326,527,61);
    private final Rectangle claseRectanglePx = new Rectangle(1944,603,307,61);
    private final Rectangle esDonanteRectanglePx = new Rectangle(867,2587,46,30);
    private final Rectangle grupoFactorRectanglePx = new Rectangle(1542,2587,665,30);
    private final Rectangle observacionesRectangle = new Rectangle(504,2708,1712,30);

    public File imprimirLicencia(LicenciaDTO licencia)
    throws IOException, URISyntaxException{
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
        map.put(nroLicenciaRectanglePx, "");
        map.put(apellidoRectanglePx,t.getApellido().toUpperCase());
        map.put(nombreRectanglePx,t.getNombre().toUpperCase());
        map.put(direccRectanglePx, t.getDireccion().toUpperCase());
        map.put(fechaNacRectanglePx,t.getFechaNacimiento().format(patronFecha));
        map.put(inicioVigRectanglePx,l.getInicioVigencia().format(patronFecha));
        map.put(finVigRectanglePx,l.getFinVigencia().format(patronFecha));
        map.put(claseRectanglePx,l.getClaseLic().getClase().toString());
        return map;
    }

    private HashMap<Rectangle,String> mapearDatosReversoLicencia(LicenciaDTO l){
        HashMap<Rectangle,String> map = new HashMap<>();
        TitularDTO t = l.getTitular();
        map.put(esDonanteRectanglePx,t.getEsDonante() ? "SI" : "NO");
        map.put(grupoFactorRectanglePx,t.getGrupoSanguineo().toString());
        map.put(observacionesRectangle, l.getObservaciones());
        return map;
    }
    
}