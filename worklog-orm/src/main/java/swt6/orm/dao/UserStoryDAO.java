package swt6.orm.dao;

import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

import java.util.List;

import static swt6.orm.domain.UserStory_.sprint;

public class UserStoryDAO extends BaseDAO {

        public static void insertUserStory(UserStory userStory) {
            insertEntity(userStory);
        }

        public static void updateSprint(UserStory userStory) {
            saveEntity(userStory);
        }

        public static void deleteSprint(UserStory userStory) {
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
}
