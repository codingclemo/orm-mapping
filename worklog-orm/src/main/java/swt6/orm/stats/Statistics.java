package swt6.orm.stats;

import swt6.orm.dao.LogbookEntryDAO;
import swt6.orm.dao.TaskDAO;
import swt6.orm.domain.Backlog;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Set;

public class Statistics {

    public static void printAverageTaskDuration() {
        Collection<Task> tasks = TaskDAO.getFinishedTasks();

        for (Task task : tasks) {
            Collection<LogbookEntry> entries = LogbookEntryDAO.getEntriesFromTask(task);
        }
    }

    public static void printEstimationDurationRation () {}

    public static void printAverageCompletedStoriesPerSprint() {}

    public static void printIncompleteTasksForSprint() {}
}
