package br.edu.ifsul.bcc.lpoo.om.model.dao;

import java.sql.PreparedStatement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.edu.ifsul.bcc.lpoo.om.model.*;

public class PersistenciaJPA implements InterfacePersistencia {
    public EntityManagerFactory factory;
    public EntityManager entity;

    public PersistenciaJPA() {
        factory = Persistence.createEntityManagerFactory("pu_db_om_lpoo_20232");
        entity = factory.createEntityManager();
    }
    
    public Boolean conexaoAberta() { return entity.isOpen(); }
    public void fecharConexao() { entity.close(); }
    public Object find(Class c, Object id) throws Exception { return entity.find(c, id); }

    public void persist(Object o) throws Exception {
        entity.getTransaction().begin();
        entity.persist(o);
        entity.getTransaction().commit();
    }

    public void remover(Object o) throws Exception {
        entity.getTransaction().begin();// abrir a transacao.
        entity.remove(o); //realiza o delete
        entity.getTransaction().commit(); //comita a transacao (comando sql)                
    }
}
