package swt6.orm.dao;

import org.hibernate.annotations.common.util.impl.Log;
import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LogbookEntryDAO extends BaseDAO {

    public static void insertLogbookEntry(LogbookEntry logbookEntry) {
        insertEntity(logbookEntry);
    }

    public static LogbookEntry updateLogbookEntry(LogbookEntry logbookEntry) {
        return saveEntity(logbookEntry);
    }

    public static void deleteLogbookEntry(LogbookEntry logbookEntry) {
        deleteEntity(logbookEntry);
    }

    public static LogbookEntry find(long logbookEntryId) {
        LogbookEntry le;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            le = em.find(LogbookEntry.class, logbookEntryId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return le;
    }

    public static LogbookEntry addTaskToLogbookEntry(LogbookEntry logbookEntry, Task task) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            //task = em.merge(task);

            logbookEntry.setTask(task);
            task.addLogbookEntry(logbookEntry);

            logbookEntry = em.merge(logbookEntry);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return logbookEntry;
    }

    public static void printTaskOfLogbookEntry(LogbookEntry logbookEntry) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Task> qry = em.createQuery(
                    "from Task where logbookEntry = :logbookEntry ",
                    Task.class);
            qry.setParameter("logbookEntry", logbookEntry);

            qry.getResultList().forEach(entry -> System.out.println( entry ));

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    public static Collection<LogbookEntry> getEntriesFromTask(Task task) {
        Collection<LogbookEntry> entries = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<LogbookEntry> qry = em.createQuery(
                    "from LogbookEntry where task = :task ",
                    LogbookEntry.class);
            qry.setParameter("task", task);

            entries.addAll(qry.getResultList());

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return  entries;
    }

    public static Collection<LogbookEntry> getEntries() {
        Collection<LogbookEntry> entries = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<LogbookEntry> qry = em.createQuery(
                    "from LogbookEntry",
                    LogbookEntry.class);
            entries.addAll(qry.getResultList());
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return  entries;
    }
}
