package isi.agiles.dao;

import java.util.Optional;

import org.hibernate.Session;

import com.google.protobuf.Option;



import isi.agiles.entidad.Usuario;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UsuarioDAO extends AbstractDAO<Usuario> {
    
    
    
    public UsuarioDAO(){
        this.setClase(Usuario.class);
    }

    public Optional<Usuario> getByUsername(String username){
        Optional<Usuario> ret;
        CriteriaBuilder cbuild=this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Usuario> cQuery=cbuild.createQuery(Usuario.class);
        Root<Usuario> root=cQuery.from(Usuario.class);
        cQuery.select(root).where(cbuild.equal(root.get("nombreUsuario"), username));
        TypedQuery<Usuario> query=this.getEntityManager().createQuery(cQuery);
        try{
            ret=Optional.ofNullable(query.getSingleResult());
        }
        catch(NoResultException n){
            ret=Optional.empty();
        }
        return ret;

       
    }
}
