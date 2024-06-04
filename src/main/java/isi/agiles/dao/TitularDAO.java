package isi.agiles.dao;

import java.util.Optional;

import org.hibernate.Session;

import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Titular;

public class TitularDAO  extends AbstractDAO<Titular>{
    public TitularDAO(){
        this.setClase(Titular.class);
    }

    public Optional<Titular> getByDocumento(String nroDoc, TipoDoc tipoDoc){
        Session session = entityManager.unwrap(Session.class);
        Optional<Titular> optional = session.byNaturalId(Titular.class).
                                             using("nroDoc", nroDoc).
                                             using("tipoDoc",tipoDoc).
                                             loadOptional();
        return optional;
    }
}
