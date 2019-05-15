package swt6.orm.dao;

import swt6.orm.domain.Project;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SprintDAO extends BaseDAO {

    public static void insertSprint(Sprint sprint) {
        insertEntity(sprint);
    }

    public static Sprint updateSprint(Sprint sprint) {
        return saveEntity(sprint);
    }

    public static void deleteSprint(Sprint sprint) {
        deleteEntity(sprint);
    }

    public static Sprint find(long sprintId) {
        Sprint s;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            s = em.find(Sprint.class, sprintId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return s;
    }

    public static Sprint assignUserStoryToSprint(Sprint sprint, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);

            for (	UserStory story : userStories) {
                //story = em.merge(story);
                story.setSprint(sprint);
                sprint.addStory(story);

            }

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return sprint;
    }

    public static void listUserStoriesOfSprint(Sprint sprint) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<UserStory> qry = em.createQuery(
                    "from UserStory where sprint = :sprint ",
                    UserStory.class);
            qry.setParameter("sprint", sprint);
            qry.getResultList().forEach(entry -> System.out.println( entry ));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    public static Collection<Sprint> getSprints() {
        Set<Sprint> sprints = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<Sprint> qry = em.createQuery(
                    "from Sprint",
                    Sprint.class);
            sprints.addAll(qry.getResultList());

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return  sprints;
    }
}
