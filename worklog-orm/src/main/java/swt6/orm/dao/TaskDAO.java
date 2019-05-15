package swt6.orm.dao;

import swt6.orm.domain.Backlog;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaskDAO extends BaseDAO {

    public static void insertTask(Task task) {
        insertEntity(task);
    }

    public static Task updateTask(Task task) {
        return saveEntity(task);
    }

    public static void deleteTask(Task task) {
        deleteEntity(task);
    }

    public static Task find(long taskId) {
        Task s;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            s = em.find(Task.class, taskId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return s;
    }

    public static Collection<Task> getTasks() {
        final Set<Task> collection = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            TypedQuery<Task> qry = em.createQuery(
                    //"from Task t where t.status = :status ",
                    "from Task t",
                    Task.class);
            //qry.setParameter("status", Task.Status.done);
            qry.getResultList().forEach(entry -> collection.add(entry));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return collection;
    }
}
