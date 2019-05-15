package swt6.orm.dao;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static swt6.orm.domain.UserStory_.sprint;

public class UserStoryDAO extends BaseDAO {

        public static void insertUserStory(UserStory userStory) {
            insertEntity(userStory);
        }

        public static UserStory updateUserStory(UserStory userStory) {
            return saveEntity(userStory);
        }

        public static void deleteUserStory(UserStory userStory) {
            deleteEntity(userStory);
        }

        public static UserStory find(long userStoryId) {
            UserStory s;
            try {
                EntityManager em = JpaUtil.getTransactedEntityManager();
                s = em.find(UserStory.class, userStoryId);
                JpaUtil.commit();
            } catch (Exception e){
                JpaUtil.rollback();
                throw e;
            }
            return s;
        }

        public static UserStory assignTaskToUserStory(UserStory userStory, Task... tasks) {
            try {
                EntityManager em = JpaUtil.getTransactedEntityManager();

                for (	Task task : tasks) {
                    task = em.merge(task);

                    if (!userStory.getTasks().contains(task)) {
                        userStory.addTask(task);
                    }
                }
                userStory = em.merge(userStory);
                JpaUtil.commit();
            } catch (Exception e){
                JpaUtil.rollback();
                throw e;
            }
            return userStory;
        }

    public static Collection<UserStory> getStories() {
        Set<UserStory> stories = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<UserStory> qry = em.createQuery(
                    "from UserStory",
                    UserStory.class);
            stories.addAll(qry.getResultList());

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return  stories;
    }
}
