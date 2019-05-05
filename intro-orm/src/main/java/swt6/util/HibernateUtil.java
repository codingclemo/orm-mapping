package swt6.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if(sessionFactory != null){
            sessionFactory.close();
            sessionFactory = null;

        }
    }

    //Version 1: only works for single threaded applications
    private static Session session;

    public static Session getSession(){
        if(session == null){
            session = getSessionFactory().openSession();
        }
        return session;
    }

    public static void closeSession(){
        if(session != null){
            session.close();
            session = null;
        }
    }

    // Version 2: for multi-threaded applications
    public static Session getCurSession(){
        return getSessionFactory().getCurrentSession();
    }
}
