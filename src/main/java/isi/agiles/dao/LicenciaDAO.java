package isi.agiles.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import isi.agiles.entidad.Licencia;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


public class LicenciaDAO extends AbstractDAO<Licencia> {
    public LicenciaDAO(){
        this.setClase(Licencia.class);
    }

    public List<Licencia> getLicenciasExpiradas(){
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Licencia> cq = cb.createQuery(Licencia.class);
        Root<Licencia> root = cq.from(Licencia.class);
        Path<LocalDate> fechaVencimientoAtrib = root.<LocalDate>get("finVigencia");
        LocalDate today = LocalDate.now();
        Predicate pasoFinVigencia = cb.lessThan(fechaVencimientoAtrib,today);
        Order masRecientePrimero = cb.desc(fechaVencimientoAtrib);

        cq.select(root).where(pasoFinVigencia);
        cq.orderBy(masRecientePrimero);

        Query<Licencia> query = session.createQuery(cq);
        List<Licencia> resultList = query.getResultList();
        
        return resultList;
    }
}