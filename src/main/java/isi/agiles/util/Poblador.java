package isi.agiles.util;

import isi.agiles.dao.TitularDAO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;

public class Poblador {
    
    public static void poblar() {
        Poblador.poblarTitular();
    }
    public static void poblarTitular(){
            TitularDAO t = new TitularDAO();
            Titular titular1= new Titular();
            titular1.setApellido("Pallotti");
            titular1.setNombre("Francisco");
            //titular1.setTipoDoc(TipoDoc.DNI);
            //titular1.setNroDoc("42925453");

            t.saveInstance(titular1);

            Titular titular2 = new Titular();
            titular2.setApellido("Pallotti");
            titular2.setNombre("Fernando");
            //titular2.setTipoDoc(TipoDoc.DNI);
            //titular2.setNroDoc("42925454");

            t.saveInstance(titular2);

    }
}
