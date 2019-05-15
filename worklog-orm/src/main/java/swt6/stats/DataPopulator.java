package swt6.stats;

import swt6.orm.dao.*;
import swt6.orm.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DataPopulator {
    private static int NO_TASKS = 100;
    private static int NO_STORIES = 50;
    private static int NO_SPRINTS = 7;
    private static int NO_PROJECTS = 2;
    private static int NO_EMPLOYEES = 5;
    private static int NO_ENTRIES = 90;

    private static int PROBABILITY_TASK = 8;

    private Random random = new Random();

    private int randInt(int min, int max) {
        int rand = random.nextInt((max - min)+1) + min;
        return rand;
    }

    private Task.Status randStatus() {
        switch (randInt(1,8)) {
            case 1: return Task.Status.open;
            case 2: return Task.Status.in_progress;
            default: return Task.Status.done;
        }
    }

    private Issue.Severity randSeverity() {
        switch (randInt(1,3)) {
            case 1: return Issue.Severity.low;
            case 2: return Issue.Severity.medium;
            default: return Issue.Severity.high;
        }
    }

    private LocalDateTime randDate() {
        LocalDateTime date = LocalDateTime.of( randInt(2018,2019), randInt(1,12), randInt(1,28), randInt(0,23), randInt(0,59));
        return date;
    }

    private LocalDateTime randBirthDate() {
        LocalDateTime date = LocalDateTime.of( randInt(1970,2000), randInt(1,12), randInt(1,28), randInt(0,23), randInt(0,59));
        return date;
    }

    private LocalDateTime randDateAfter(LocalDateTime date, long hours) {
        date = date.plusHours(hours);
        return date;
    }

    public void generateTasks() {
        System.out.println("#### Generating tasks...");
        for (int i = 0; i < NO_TASKS; i++) {

            long duration = 1000 * 60 * 30 * randInt(1,10);

            if (randInt(0,10) < PROBABILITY_TASK) {
                Task task = new Task("TaskTitle"+i, "TaskDescription"+i, duration, randStatus() );
                TaskDAO.insertTask(task);
            } else {
                LocalDateTime randDate = randDate();
                Issue issue = new Issue("IssueTitle"+i, "IssueDescription"+i,duration,randStatus(),
                        randDate, randDateAfter(randDate, randInt(1,10)), randSeverity());
                TaskDAO.insertTask(issue);
            }
        }
        System.out.println("#### Tasks are finished.");
    }

    public void generateUserStories() {
        System.out.println("#### Generating stories...");
        // create Story
        Collection<UserStory> stories = new HashSet<>();
        for (int i = 0; i < NO_STORIES; i++) {
            UserStory story = new UserStory("Story"+i, "Desc"+i, 0);
            stories.add(story);
        }

        // add Tasks
        Collection<Task> tasks = TaskDAO.getTasks();
        Iterator<Task> it = tasks.iterator();
        int tasksPerStory = tasks.size() / NO_STORIES;

        while(it.hasNext()) {
            for (UserStory story : stories) {
                if (it.hasNext()) {
                    Task toAdd = it.next();
                    story.addTask(toAdd);
                }
            }
        }

        // update Estimates
        for (UserStory story : stories) {
            long sum = 0;
            for (Task task :
                    story.getTasks()) {
                sum += task.getEstimate();
            }
            story.setEstimate(sum);
            UserStoryDAO.updateUserStory(story);
        }
        System.out.println("#### Stories are finished.");
    }

    public void generateProjects() {
        System.out.println("#### Generating projects...");

        Collection<Project> projects = new HashSet<>();
        Collection<Backlog> backlogs = new HashSet<>();
        for (int i = 0; i < NO_PROJECTS; i++){
            Project project = new Project("Project"+i);
            projects.add(project);

            Backlog backlog = new Backlog("Vision"+i, "Descri"+i);
            backlogs.add(backlog);

            ProjectDAO.assignBacklogToProject(project,backlog);
        }

        System.out.println("#### Projects are finished.");
    }

    public void generateSprints() {
        System.out.println("#### Generating sprints...");

        Collection<Sprint> sprints = new HashSet<>();
        for (int i = 0; i < NO_SPRINTS; i++) {
            LocalDateTime start = randDate();
            Sprint sprint = new Sprint(start, randDateAfter(start, 24*7*2));
            sprint = SprintDAO.updateSprint(sprint);
            sprints.add(sprint);
        }


        Collection<Backlog> backlogs = BacklogDAO.getBacklogs();
        Collection<UserStory> stories = UserStoryDAO.getStories();
        Iterator<UserStory> it = stories.iterator();
        while (it.hasNext()) {
            for (Sprint sprint :
                    sprints) {
                if (it.hasNext()) {
                    sprint.addStory(it.next());
                }
            }
            for (Backlog backlog : backlogs) {
                if (it.hasNext()) {
                    backlog.addStory(it.next());
                }
            }
        }

        Object[] projects = ProjectDAO.getProjects().toArray();
        int length = projects.length;
        for (Sprint sprint :
                sprints) {
            int rand = randInt(0,length-1);
            Project project = (Project) projects[rand];
            ProjectDAO.assignSprintsToProject(project, sprint);
            sprint = SprintDAO.updateSprint(sprint);
        }

        for (Backlog backlog :
                backlogs) {
            BacklogDAO.updateBacklog(backlog);
        }

        System.out.println("#### Sprints are finished.");
    }

    public void generateEmployees() {
        System.out.println("#### Generating employees...");

        Collection<Project> projects = ProjectDAO.getProjects();
        Project project1 = null;
        Project project2 = null;
        for (Project project : projects) {
            if (project1 == null) {
                project1 = project;
            } else {
                project2 = project;
            }
        }

        Collection<Employee> employees = new HashSet<>();
        for (int i = 0; i < NO_EMPLOYEES; i++) {
            Employee employee;
            if (randInt(0,2) % 2 == 0) {
                employee = new PermanentEmployee("firstname"+i, "lastname"+i, randBirthDate().toLocalDate());
                ((PermanentEmployee) employee).setSalary((double) randInt(1000, 5000));
            } else {
                employee = new TemporaryEmployee("firstname"+i, "lastname"+i, randBirthDate().toLocalDate());
                LocalDateTime start = randDate();
                ((TemporaryEmployee) employee).setStartDate(start.toLocalDate());
                ((TemporaryEmployee) employee).setEndDate(randDateAfter(start, randInt(8000,100000)).toLocalDate());
            }

            //add address
            Address address = new Address(String.valueOf(randInt(1000,9999)), "city"+i, "street"+i*i);
            employee.setAddress(address);

            employees.add(EmployeeDAO.updateEmployee(employee));
        }

        for(Employee employee : employees) {
            if (randInt(1,2) % 2 == 0)
                EmployeeDAO.assignProjectsToEmployee(employee, project1);
            else
                EmployeeDAO.assignProjectsToEmployee(employee, project2);
        }

        System.out.println("#### Employees are finished.");
    }



    public void generateLogbookEntries() {
        System.out.println("#### Generate logbook entries...");

        Collection<LogbookEntry> entries = new HashSet<>();


        for (int i = 0; i < NO_ENTRIES; i++) {
            LocalDateTime start = randDate();
            LogbookEntry logbookEntry = new LogbookEntry("activity" + i, start, randDateAfter(start, randInt(1, 3)));
            //task.getLogbookEntries().add(logbookEntry);
            //logbookEntry.setTask(task);
            //logbookEntry = LogbookEntryDAO.addTaskToLogbookEntry(logbookEntry, task);
            entries.add(logbookEntry);
        }


        Collection<Employee> employees = EmployeeDAO.getEmployees();
        Iterator<LogbookEntry> lit = entries.iterator();
        while(lit.hasNext()) {
            for (Employee employee :
                    employees) {
                if (lit.hasNext())
                    EmployeeDAO.addLogbookEntries(employee, lit.next());
            }
        }

        entries = LogbookEntryDAO.getEntries();

        Collection<Task> tasks = TaskDAO.getTasks();


        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {

            for (LogbookEntry entry :
                    entries) {
                if (it.hasNext()) {
                    Task task = it.next();
                    LogbookEntryDAO.addTaskToLogbookEntry(entry, task);
                }
            }
        }
        System.out.println("#### Logbook entries are generated.");
    }
}
