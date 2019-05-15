package swt6.orm.dao;

import swt6.orm.domain.Backlog;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;

public class BacklogDAO extends BaseDAO {

    public static void insertBacklog(Backlog backlog) {
        insertEntity(backlog);
    }

    public static Backlog updateBacklog(Backlog backlog) {
        return saveEntity(backlog);
    }

    public static void deleteBacklog(Backlog backlog) {
        deleteEntity(backlog);
    }

    public static Backlog find(long backlogId) {
        Backlog bl;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            bl = em.find(Backlog.class, backlogId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return bl;
    }

    public static Backlog attachUserStoryToBacklog(Backlog backlog, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            backlog = em.merge(backlog);
            for (	UserStory story : userStories) {
                //story = em.merge(story);
                backlog.addStory(story);
            }
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return backlog;
    }

    public static Backlog detachUserStoryFromBacklog(Backlog backlog, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            for (	UserStory story : userStories) {
                story = em.merge(story);
                backlog.removeStory(story);
            }
            backlog = em.merge(backlog);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return backlog;
    }

    public static void listUserStoriesOfBacklog(Backlog backlog) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<UserStory> qry = em.createQuery(
                    "from UserStory where backlog = :backlog ",
                    UserStory.class);
            qry.setParameter("backlog", backlog);
            qry.getResultList().forEach(entry -> System.out.println( entry ));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    public static Collection<Backlog> getBacklogs() {
        Collection<Backlog> backlogs = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<Backlog> qry = em.createQuery(
                    "from Backlog",
                    Backlog.class);
            qry.getResultList().forEach(entry -> backlogs.add(entry));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return backlogs;
    }
}
