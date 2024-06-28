package isi.agiles.logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import javafx.util.Pair;

public class GestorImpresion {

    public static final float A4_HEIGHT_PX = 3508F;
    public static final float A4_HEIGHT_UU = 842F;
    public static final float A4_WIDTH_PX = 2480F;
    public static final float A4_WIDTH_UU = 595F;

    /*Retorna un par con el objeto PdfDocument abierto para poder modificar el archivo,
     *mas el archivo donde se ubica el resultado.
     */
    public Pair<PdfDocument,File> crearPdfDesdePlantilla(String templatePath, String outputFilename)
    throws FileNotFoundException, IOException, URISyntaxException{
        //Se ubica al archivo de output en la carpeta de recursos pdf
        File templateFile = new File(this.getClass().getResource(templatePath).toURI());
        File outputFile = new File(templateFile.getParentFile(),outputFilename);
        //Reader y Writer lanzan IOException si hay algun problema leyendo o escribiendo los archivos
        PdfReader reader = new PdfReader(templateFile);
        PdfWriter writer = new PdfWriter(outputFile);
        return new Pair<>(new PdfDocument(reader,writer),outputFile);
    }

    public Rectangle convertToUserUnits(Rectangle rectanglePx, float offsetX, float offsetY){
        float x = (rectanglePx.getX() + offsetX) * (A4_WIDTH_UU/A4_WIDTH_PX) ;
        float y = (1 - (rectanglePx.getY() + offsetY)/A4_HEIGHT_PX) * A4_HEIGHT_UU;
        float width = rectanglePx.getWidth() * (A4_WIDTH_UU/A4_WIDTH_PX);
        float height = rectanglePx.getHeight() * (A4_HEIGHT_UU/A4_HEIGHT_PX);
        return new Rectangle(x,y,width,height);
    }

    protected void imprimirDatos(PdfPage pdfPage, Map<Rectangle,String> datos, PdfFont font, Integer fontSize){
        for(Map.Entry<Rectangle,String> entry : datos.entrySet()){
            Paragraph para = new Paragraph(entry.getValue());
            Rectangle area = this.convertToUserUnits(entry.getKey(),0,45);
            para.setFont(font);
            para.setFontSize(fontSize);
            try(Canvas canvas = new Canvas(pdfPage,area)){
                canvas.add(para);
            }
        }
    }
}
