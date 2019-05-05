package swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtil {
  private static EntityManagerFactory emFactory;
  private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

  public static synchronized EntityManagerFactory getEntityManagerFactory() {
    //
    // TODO
    //
    
    return null;
  }

  public static synchronized EntityManager getEntityManager() {
    //
    // TODO
    //
    
    return null;
  }

  public static synchronized void closeEntityManager() {
    //
    // TODO
    //
  }

  public static synchronized EntityManager getTransactedEntityManager() {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    //
    // TODO
    //
    
    return em;
  }

  public static synchronized void commit() {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();

    //
    // TODO
    //
  }

  public static synchronized void rollback() {
    EntityManager em = getEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    //
    // TODO
    //
  }

  public static synchronized void closeEntityManagerFactory() {
    //
    // TODO
    //
  }
}
