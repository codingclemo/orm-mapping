package swt6.orm.dao;

import swt6.orm.domain.Backlog;
import swt6.orm.domain.Project;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class BaseDAO {

    protected static <T> void insertEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();

        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    protected static <T> T saveEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            JpaUtil.commit();

        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return entity;
    }

    protected static <T> boolean deleteEntity(T entity) {
        boolean result = true;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            result = false;
            throw e;
        }
        return result;
    }
}
