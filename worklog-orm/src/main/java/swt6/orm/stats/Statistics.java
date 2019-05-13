package swt6.orm.stats;

import swt6.orm.dao.LogbookEntryDAO;
import swt6.orm.dao.SprintDAO;
import swt6.orm.dao.TaskDAO;
import swt6.orm.dao.UserStoryDAO;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;

import java.time.Duration;
import java.util.Collection;

public class Statistics {

    public static void printAverageTaskDuration() {
        System.out.println("---- printAverageTaskDuration -----");
        Collection<Task> tasks = TaskDAO.getTasks();
        long cnt = 0;
        long sum = 0;
        for (Task task : tasks) {
            Collection<LogbookEntry> entries = LogbookEntryDAO.getEntriesFromTask(task);
            for (LogbookEntry entry : entries) {
                //calc duration
                Duration duration = Duration.between(entry.getStartTime(), entry.getEndTime());
                sum += duration.toMinutes();
            }
            cnt++;
        }
        System.out.println("On average, a task is processed in " + sum / cnt + " minutes (total: " + sum +")");
    }

    public static void printEstimationDurationRation () {
        System.out.println("---- printEstimationDurationRation -----");
        Collection<Task> tasks = TaskDAO.getTasks();
        long estimateSum = 0;
        long durationSum = 0;
        for (Task task : tasks) {
            estimateSum += task.getEstimate();
            Collection<LogbookEntry> entries = LogbookEntryDAO.getEntriesFromTask(task);
            for (LogbookEntry entry : entries) {
                Duration duration = Duration.between(entry.getStartTime(), entry.getEndTime());
                durationSum += duration.toMinutes();
            }
        }
        if(estimateSum > durationSum)
            System.out.println("A task is estimate with a ration of " + estimateSum / durationSum + ":1" +
                " (total estimated: " + estimateSum +" total duration: " + durationSum + ")");
        else
            System.out.println("A task is estimate with a ration of 1:" + estimateSum / durationSum +
                    " (total estimated: " + estimateSum +" total duration: " + durationSum + ")");
    }

    public static void printAverageCompletedStoriesPerSprint() {
        System.out.println("---- printAverageCompletedStoriesPerSprint -----");
        Collection<UserStory> stories = UserStoryDAO.getStories();
        long sumStories = 0;
        long sumCompleted = 0;

        for (UserStory story : stories) {
            Collection<Task> tasks = story.getTasks();
            long sumTasks = tasks.size();
            long tasksCompleted = 0;
            for (Task task : tasks) {
                if (task.getStatus().equals(Task.Status.done)) {
                    tasksCompleted++;
                }
            }
            if (sumTasks != 0 && sumTasks == tasksCompleted) sumCompleted++;
            sumStories++;
        }
        System.out.println("On average, " + sumCompleted / sumStories + " stories are completed per sprint (total sprints: " + sumCompleted +")");
    }

    public static void printIncompleteTasksForSprint() {
        System.out.println("---- printIncompleteTasksForSprint -----");
        Collection<Sprint> sprints = SprintDAO.getSprints();
        long sumSprints = 0;
        long sumCompleted = 0;
        long sumIncomplete = 0;

        for (Sprint sprint : sprints) {
            sumSprints++;
            Collection<UserStory> stories = sprint.getUserStories();
            for (UserStory story : stories) {
                for (Task task : story.getTasks()) {
                    sumCompleted++;
                    if (!task.getStatus().equals(Task.Status.done)) sumIncomplete++;
                }
            }
        }
        System.out.println("On average, " + sumIncomplete / sumSprints +
                " tasks are incomplete at the end of the sprint (total tasks: " + sumCompleted +", total sprints: " + sumSprints +")");
    }
}
