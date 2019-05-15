package swt6.orm.jpa;


import swt6.orm.dao.*;
import swt6.orm.domain.*;
import swt6.stats.DataPopulator;
import swt6.stats.Statistics;
import swt6.util.JpaUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import static swt6.orm.dao.EmployeeDAO.*;
import static swt6.orm.dao.LogbookEntryDAO.addTaskToLogbookEntry;
import static swt6.orm.dao.ProjectDAO.*;
import static swt6.orm.dao.SprintDAO.assignUserStoryToSprint;
import static swt6.orm.dao.SprintDAO.listUserStoriesOfSprint;

public class WorkLogManager {

	public static void main(String[] args) throws IOException {
		//initialTests();
		DataPopulator populator = new DataPopulator();

		populator.generateTasks();
		populator.generateUserStories();
		populator.generateProjects();
		populator.generateSprints();
		populator.generateEmployees();
        populator.generateLogbookEntries();
/*
        Statistics.printAverageCompletedStoriesPerSprint();
        Statistics.printAverageTaskDuration();
        Statistics.printEstimationDurationRation();
        Statistics.printIncompleteTasksForSprint();
*/

		showHomeScreen();
	}

	private static void showHomeScreen() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager\n" +
				"******************************************\n" +
				"\n" +

				"1...Statistics\n" +
				"2...Add Entities\n"+
				"3...Attach Entities\n"+
				"4...Quit\n");

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));

		String selection = reader.readLine();

		switch (selection) {
			case "1": showStatisticsOptions(); break;
			case "2": showEntitiesToAdd(); break;
			case "3": showEntitiesToAttach(); break;
			case "4": System.exit(0); break;
			default: showHomeScreen(); System.out.println( selection + " is not valid. Please select a valid option."); break;
		}
	}

	private static void showStatisticsOptions() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Statistics\n" +
				"******************************************\n" +
				"1...Average Completed Stories Per Sprint\n" +
				"2...Average Task Duration\n"+
				"3...Incomplete Tasks For Sprint\n"+
				"4...Estimation Duration Ration\n" +
				"5...Back\n");


		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));

		String selection = reader.readLine();

		switch (selection) {
			case "1": Statistics.printAverageCompletedStoriesPerSprint(); showStatisticsOptions(); break;
			case "2": Statistics.printAverageTaskDuration(); showStatisticsOptions(); break;
			case "3": Statistics.printIncompleteTasksForSprint(); showStatisticsOptions(); break;
			case "4": Statistics.printEstimationDurationRation(); showStatisticsOptions(); break;
			case "5": showHomeScreen(); break;
			default: System.out.println( selection + " is not valid. Please select a valid option."); showStatisticsOptions(); break;
		}
	}

	private static void showEntitiesToAdd() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Add entities\n" +
				"******************************************\n" +
				"1...Add Sprint\n" +
				"2...Add Backlog\n"+
				"3...Add UserStory\n"+
				"4...Add Task\n" +
				"5...Add Issue\n" +
				"6...Back\n");

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));

		String selection = reader.readLine();

		switch (selection) {
			case "1": showAddSprint(); showEntitiesToAdd(); break;
			case "2": showAddBacklog(); showEntitiesToAdd(); break;
			case "3": showAddUserStory(); showEntitiesToAdd(); break;
			case "4": showAddTask(); showEntitiesToAdd(); break;
			case "5": showAddIssue(); showEntitiesToAdd(); break;
			case "6": showHomeScreen(); break;
			default: System.out.println( selection + " is not valid. Please select a valid option."); showEntitiesToAdd(); break;
		}
	}

	private static void showEntitiesToAttach() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Attach entities\n" +
				"******************************************\n" +
				"1...Attach UserStory to Sprint\n" +
				"2...Attach UserStory to Backlog\n" +
				"3...Attach Task to UserStory\n"+
				"4...Attach Issue to UserStory\n"+
				"5...Back\n");

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String selection = reader.readLine();

		switch (selection) {
			case "1": showAttachStoryToSprint(); showEntitiesToAttach(); break;
			case "2": showAttachStoryToBacklog(); showEntitiesToAttach(); break;
			case "3": showAttachTaskToStory(); showEntitiesToAttach(); break;
			case "4": showAttachIssueToStory(); showEntitiesToAttach(); break;
			case "5": showHomeScreen(); break;
			default: System.out.println( selection + " is not valid. Please select a valid option."); showEntitiesToAdd(); break;
		}
	}


	private static void showAttachIssueToStory() throws IOException {
		Issue issue = null;
		UserStory story = null;
		while (issue == null) {
			long issueId = getNumber("Enter Issue ID:\n");
			issue = (Issue) TaskDAO.find(issueId);
		}
		while (story == null) {
			long storyId = getNumber("Enter User Story ID:\n");
			story = UserStoryDAO.find(storyId);
		}
		story = UserStoryDAO.assignTaskToUserStory(story, issue);
		issue = (Issue) TaskDAO.find(issue.getId());
		System.out.println("Attached Story: \n" + story + "\nwith Issue:\n" + issue);
	}

	private static void showAttachTaskToStory() throws IOException {
		Task task = null;
		UserStory story = null;
		while (task == null) {
			long taskId = getNumber("Enter Task ID:\n");
			task = (Issue) TaskDAO.find(taskId);
		}
		while (story == null) {
			long storyId = getNumber("Enter User Story ID:\n");
			story = UserStoryDAO.find(storyId);
		}
		story = UserStoryDAO.assignTaskToUserStory(story, task);
		task = TaskDAO.find(task.getId());
		System.out.println("Attached Story: \n" + story + "\nwith Task:\n" + task);
	}

	private static void showAttachStoryToBacklog() throws IOException {
		UserStory story = null;
		Backlog backlog = null;
		while (story == null) {
			long storyId = getNumber("Enter User Story ID:\n");
			story = UserStoryDAO.find(storyId);
		}
		while (backlog == null) {
			long backlogId = getNumber("Enter Backlog ID:\n");
			backlog = BacklogDAO.find(backlogId);
		}
		backlog = BacklogDAO.attachUserStoryToBacklog(backlog, story);
		story = UserStoryDAO.find(story.getId());
		System.out.println("Attached Backlog: \n" + backlog + "\nwith Story:\n" + story);
	}

	private static void showAttachStoryToSprint() throws IOException {
		UserStory story = null;
		Sprint sprint = null;
		while (story == null) {
			long storyId = getNumber("Enter User Story ID:\n");
			story = UserStoryDAO.find(storyId);
		}
		while (sprint == null) {
			long sprintId = getNumber("Enter Sprint ID:\n");
			sprint = SprintDAO.find(sprintId);
		}
		sprint = SprintDAO.assignUserStoryToSprint(sprint, story);
		story = UserStoryDAO.find(story.getId());
		System.out.println("Attached Sprint: \n" + sprint + "\nwith Story:\n" + story);
	}

	private static void showAddSprint() throws IOException {
        System.out.println(
                        "******************************************\n" +
                        "WorkLogManager - Add Sprint\n" +
                        "******************************************\n");

        LocalDateTime start = getDate("Enter start date: dd.MM.YYYY\n");
        LocalDateTime end = getDate("Enter end date: dd.MM.YYYY\n");

        Sprint sprint = new Sprint(start, end);
        sprint = SprintDAO.updateSprint(sprint);

        System.out.println("New entity added:");
        System.out.println(sprint);
    }

    private static LocalDateTime getDate(String imperativeText) throws IOException {
        Date date = null;
        while(date == null) {
            System.out.println(imperativeText);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            String selection = reader.readLine();

            try {
                date = new SimpleDateFormat("dd.MM.YYYY").parse(selection);
            } catch (ParseException e) {
                System.out.println( selection + " is not valid. Enter date in format: dd.MM.YYYY\n");
                getDate(imperativeText);
            }
        }

        LocalDateTime result;
        result = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Europe/Berlin"));
        return result;
    }

    private static String getText(String imperativeText) throws IOException {
		System.out.println(imperativeText);
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String selection = reader.readLine();
		return selection;
	}

	private static long getNumber(String imperativeText) throws IOException {
		System.out.println(imperativeText);
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String selection = reader.readLine();

		long number = 0;
		try {
			number = Long.parseLong(selection);
		} catch (Exception e) {
			System.out.println( selection + " is not valid.\n");
			getNumber(imperativeText);
		}
		return number;
	}

    private static void showAddBacklog() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Add Backlog\n" +
				"******************************************\n");

		String vision = getText("Enter vision:\n");
		String description = getText("Enter description:\n");

		Backlog backlog = new Backlog(vision, description);
		backlog = BacklogDAO.updateBacklog(backlog);

		System.out.println("New entity added:");
		System.out.println(backlog);
    }

    private static void showAddUserStory() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Add UserStory\n" +
				"******************************************\n");

		String title = getText("Enter title:\n");
		String description = getText("Enter description:\n");
		Long estimate = getNumber("Enter estimate (in milliseconds):\n");

		UserStory story = new UserStory(title, description, estimate);
		story = UserStoryDAO.updateUserStory(story);

		System.out.println("New entity added:");
		System.out.println(story);
    }

    private static void showAddTask() throws IOException {
		System.out.println(
				"******************************************\n" +
				"WorkLogManager - Add Task\n" +
				"******************************************\n");

		String title = getText("Enter title:\n");
		String description = getText("Enter description:\n");
		Long estimate = getNumber("Enter estimate (in milliseconds):\n");

		System.out.println("Set status:\n" +
				"1...Open\n" +
				"2...In progress\n"+
				"3...Done\n");

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String selection = reader.readLine();
		Task.Status status;

		switch (selection) {
			case "1": status = Task.Status.open; break;
			case "2": status = Task.Status.in_progress; break;
			case "3": status = Task.Status.done; break;
			default: status = Task.Status.open; break;
		}

		Task task = new Task(title, description, estimate, status);
		task = TaskDAO.updateTask(task);

		System.out.println("New entity added:");
		System.out.println(task);
    }

    private static void showAddIssue() throws IOException {
		System.out.println(
				"******************************************\n" +
						"WorkLogManager - Add Issue\n" +
						"******************************************\n");

		String title = getText("Enter title:\n");
		String description = getText("Enter description:\n");
		Long estimate = getNumber("Enter estimate (in milliseconds):\n");

		System.out.println("Set status:\n" +
				"1...Open\n" +
				"2...In progress\n"+
				"3...Done\n");

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(System.in));
		String selection = reader.readLine();
		Task.Status status;
		switch (selection) {
			case "1": status = Task.Status.open; break;
			case "2": status = Task.Status.in_progress; break;
			case "3": status = Task.Status.done; break;
			default: status = Task.Status.open; break;
		}

		LocalDateTime found = getDate("Enter found date: dd.MM.YYYY\n");
		LocalDateTime fixed = getDate("Enter fixed date: dd.MM.YYYY\n");

		System.out.println("Set severity:\n" +
				"1...Low\n" +
				"2...Medium\n"+
				"3...High\n");

		selection = getText("Set severity:\n");

		Issue.Severity severity;
		switch (selection) {
			case "1": severity = Issue.Severity.low; break;
			case "2": severity = Issue.Severity.medium; break;
			case "3": severity = Issue.Severity.high; break;
			default: severity = Issue.Severity.low; break;
		}

		Issue issue = new Issue(title, description, estimate, status, found, fixed, severity);
		issue = (Issue) TaskDAO.updateTask(issue);

		System.out.println("New entity added:");
		System.out.println(issue);
    }



    private static void initialTests(){
		try {
			System.out.println("----- create schema -----");
			JpaUtil.getEntityManagerFactory();

			PermanentEmployee pe = new PermanentEmployee("Franz", "Mayr", LocalDate.of(1980, 12, 24));
			pe.setAddress(new Address("4232", "Hagenberg", "Hauptstraße 1"));
			pe.setSalary(5000.0);
			Employee empl1 = pe;

			TemporaryEmployee te = new TemporaryEmployee("Bill", "Gates", LocalDate.of(1970, 1, 21));
			te.setAddress(new Address("77777", "Redmond", "Clinton Street"));
			te.setHourlyRate(50.0);
			te.setRenter("Microsoft");
			te.setStartDate(LocalDate.of(2018, 1, 1));
			te.setEndDate(LocalDate.of(2018, 3, 1));
			Employee empl2 = te;

			TemporaryEmployee te1 = new TemporaryEmployee("Schmill", "Fates", LocalDate.of(1970, 1, 21));
			te1.setAddress(new Address("77777", "Redmond", "Clinton Street"));
			te1.setHourlyRate(50.0);
			te1.setRenter("Nitrosoft");
			te1.setStartDate(LocalDate.of(2018, 1, 1));
			te1.setEndDate(LocalDate.of(2018, 3, 1));
			Employee empl3 = te1;

			/* LogbookEntries */
			LogbookEntry entry1 = new LogbookEntry("Analyse", LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 1, 15, 30));
			LogbookEntry entry2 = new LogbookEntry("Implementierung", LocalDateTime.of(2018, 2, 1, 8, 45),
					LocalDateTime.of(2018, 2, 1, 17, 15));
			LogbookEntry entry3 = new LogbookEntry("Testen", LocalDateTime.of(2018, 2, 1, 12, 30),
					LocalDateTime.of(2018, 2, 1, 17, 0));

			System.out.println("------ insert entity -------");
			empl1 = EmployeeDAO.updateEmployee(empl1);

			System.out.println("------ save entity -------");
			empl2 = EmployeeDAO.updateEmployee(empl2);

			System.out.println("------ save entity -------");
			empl3 = EmployeeDAO.updateEmployee(empl3);

			System.out.println("------ list employees -------");
			EmployeeDAO.listEmployees();

			System.out.println("----- addLogbookEntries -----");
			empl1 = addLogbookEntries(empl1, entry1, entry2);
			empl2 = addLogbookEntries(empl2, entry3);


			//employee get logbookentry add addTaskToLogbookEntry()
			Task task1 = new Task("tasktitle1", "taskdesc1", 10000, Task.Status.open);


			empl2.getLogbookEntries().forEach( entry -> {
				if (entry.getActivity().equals(entry3.getActivity())) {
					entry = addTaskToLogbookEntry(entry, task1 );
				}
			});

			//need to get the updated employee including the linked tasks from the dbs
			empl2 = EmployeeDAO.find(empl2.getId());
			EmployeeDAO.listEntriesOfEmployee(empl2);


			//add backlog to project

			Project p1 = new Project("Office");
			Project p2 = new Project("Enterprise Server");
			p1 = ProjectDAO.updateProject(p1);
			p2 = ProjectDAO.updateProject(p2);

			System.out.println("----- Backlog of project -----");
			Backlog backlog1 = new Backlog("BacklogVision","BacklogDescription");
			backlog1 = BacklogDAO.updateBacklog(backlog1);

			p1 = assignBacklogToProject(p1, backlog1);
			backlog1 = BacklogDAO.find(p1.getBacklog().getId());

			listBacklogOfProject(p1);


			//add sprint to project
			System.out.println("----- create Sprints -----");
			Sprint sprint1 = new Sprint(
					LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 14, 10, 15));

			Sprint sprint2 = new Sprint(
					LocalDateTime.of(2019, 3, 1, 10, 15),
					LocalDateTime.of(2019, 3, 14, 10, 15));

			p1 = assignSprintsToProject(p1, sprint1, sprint2);

			System.out.println("----- Sprints of project -----");
			listSprintsOfProject(p1);

			//add userstory to sprint
			System.out.println("----- User stories -----");
			UserStory userStory1 = new UserStory("USTitle", "USDescription", 34000);
			UserStory userStory2 = new UserStory("USTitle2", "USDescription2", 35000);
			UserStory userStory3 = new UserStory("USTitle3", "USDescription3", 90000);

			p1 = ProjectDAO.find(p1.getId());
			for(Sprint sprint : p1.getSprints()) {
				if(sprint.getStartDate().equals(LocalDateTime.of(2018, 2, 1, 10, 15))) {
					sprint1 = assignUserStoryToSprint(sprint, userStory1, userStory2);
				}
			}
			sprint1 = SprintDAO.find(sprint1.getId());

			System.out.println("----- User stories of a sprint -----");
			listUserStoriesOfSprint(sprint1);

			//add userstory to backlog
			backlog1 = BacklogDAO.find(p1.getBacklog().getId());
			backlog1 = BacklogDAO.attachUserStoryToBacklog(backlog1, userStory3);

			//add task to userstory
			Task task2 = new Task("tasktitle2", "taskdesc2", 20000, Task.Status.in_progress);
			Issue issue1 = new Issue("issuetitle1", "issuedesc1", 10000, Task.Status.done,
					LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 14, 10, 15),
					Issue.Severity.low);


			for ( UserStory userStory : sprint1.getUserStories()) {
				if (userStory.getTitle().equals(userStory1.getTitle())) {
					userStory1 = UserStoryDAO.find(userStory.getId());
				}
			}
			userStory1 = UserStoryDAO.assignTaskToUserStory(userStory1, task2, issue1);
			System.out.println("----- List UserStories of Backlog -----");
			BacklogDAO.listUserStoriesOfBacklog(backlog1);


			// add projects to employee
			System.out.println("----- assignProjectsToEmployees -----");
			empl1 = EmployeeDAO.find(empl1.getId());
			empl2 = EmployeeDAO.find(empl2.getId());
			p1 = ProjectDAO.find(p1.getId());
			p2 = ProjectDAO.find(p2.getId());

			empl1 = assignProjectsToEmployee(empl1, p1, p2);
			empl2 = assignProjectsToEmployee(empl2, p2);

			ProjectDAO.listMembersOfProject(p1);
			ProjectDAO.listMembersOfProject(p2);

				/* NOT WORKING
				EmployeeDAO.listProjects(empl1);
				EmployeeDAO.listProjects(empl2);
				*/

			Statistics.printAverageTaskDuration();
			Statistics.printEstimationDurationRation();
			Statistics.printAverageCompletedStoriesPerSprint();
			Statistics.printIncompleteTasksForSprint();
		} finally {
			//JpaUtil.closeEntityManagerFactory();
		}
	}
}