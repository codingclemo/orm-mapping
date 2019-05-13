package swt6.orm.jpa;

import javax.persistence.*;


import swt6.orm.dao.*;
import swt6.orm.domain.*;
import swt6.orm.stats.Statistics;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static swt6.orm.dao.EmployeeDAO.*;
import static swt6.orm.dao.LogbookEntryDAO.addTaskToLogbookEntry;
import static swt6.orm.dao.LogbookEntryDAO.printTaskOfLogbookEntry;
import static swt6.orm.dao.ProjectDAO.*;
import static swt6.orm.dao.SprintDAO.assignUserStoryToSprint;
import static swt6.orm.dao.SprintDAO.listUserStoriesOfSprint;

public class WorkLogManager {
/*
	@SuppressWarnings("unused")
	private static void insertEmployee1(Employee empl) {
		//Worklog Persistance Unit
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("WorklogPU");
		EntityManager em = null;
		EntityTransaction tx = null;

		try {
			em = emFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			em.persist(empl);
			tx.commit();

		} catch (Exception e){
			if (tx != null && tx.isActive()){
				tx.rollback();
			}
		}
		finally{
			if (em != null){
				em.close();
			}
			emFactory.close();
		}
	}

	private static void insertEmployee(Employee empl) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			em.persist(empl);
			JpaUtil.commit();

		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

	private static void testFetchingStrategies() {

		// prepare: fetch valid ids for employee and logbookentry
		Long entryId = null;
		Long emplId = null;

		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			Optional<LogbookEntry> entry =
					em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
							.setMaxResults(1)
							.getResultList().stream().findAny();
			if (!entry.isPresent()) return;
			entryId = entry.get().getId();

			Optional<Employee> empl =
					em.createQuery("select e from Employee e", Employee.class)
							.setMaxResults(1)
							.getResultList().stream().findAny();
			if (!empl.isPresent()) return;
			emplId = empl.get().getId();

			JpaUtil.commit();
		}
		catch (Exception e) {
			JpaUtil.rollback();
			throw e;
		}

		System.out.println("############################################");

		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			System.out.println("###> Fetching LogbookEntry ...");
			LogbookEntry entry = em.find(LogbookEntry.class, entryId);
			System.out.println("###> Fetched LogbookEntry");
			Employee empl1 = entry.getEmployee();
			System.out.println("###> Fetched associated Employee");
			System.out.println(empl1);
			System.out.println("###> Accessed associated Employee");

			JpaUtil.commit();
		}
		catch (Exception e) {
			JpaUtil.rollback();
			throw e;
		}

		System.out.println("############################################");

		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			System.out.println("###> Fetching Employee ...");
			Employee empl2 = em.find(Employee.class, emplId);
			System.out.println("###> Fetched Employee");
			// Force Lazy Initialization exception
			em.clear();
			Set<LogbookEntry> entries = empl2.getLogbookEntries();
			System.out.println("###> Fetched associated entries");
			for (LogbookEntry e : entries)
				System.out.println("  " + e);
			System.out.println("###> Accessed associated entries");

			JpaUtil.commit();
		}
		catch (Exception e) {
			JpaUtil.rollback();
			throw e;
		}

		System.out.println("############################################");

	}
*/
	public static void main(String[] args) {
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


			Statistics.printAverageTaskDuration();


			Statistics.printEstimationDurationRation();
			Statistics.printAverageCompletedStoriesPerSprint();
			Statistics.printIncompleteTasksForSprint();

/* NOT WORKING
			ProjectDAO.listMembersOfProject(p1);
			ProjectDAO.listMembersOfProject(p2);

			EmployeeDAO.listProjects(empl1);
			EmployeeDAO.listProjects(empl2);
			*/

/*

			System.out.println("----- listLogbookEntriesOfEmployee -----");
			listEntriesOfEmployee(empl1);

			System.out.println("----- loadEmployeesWithEntries -----");
			loadEmployeesWithEntries();

			System.out.println("----- listEntriesOfEmployeeCQ -----");
			listEntriesOfEmployeeCQ(empl1);


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



			System.out.println("----- User stories -----");
			UserStory userStory1 = new UserStory("USTitle", "USDescription", 34000);
			UserStory userStory2 = new UserStory("USTitle2", "USDescription2", 35000);
			sprint1 = assignUserStoryToSprint(sprint1, userStory1, userStory2);
			System.out.println("----- User stories of a sprint -----");
			listUserStoriesOfSprint(sprint1);

			Task task1 = new Task("tasktitle", "taskdesc", 70000, Task.Status.open);
			Task task2 = new Task("tasktitle", "taskdesc", 70000, Task.Status.open);
			Issue issue1 = new Issue("issuetitle", "issuedesc", 70000, Task.Status.open,
					LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 14, 10, 15),
					Issue.Severity.low);

			System.out.println("----- addTaskToLogbookEntry -----");
			empl1 = addLogbookEntries(empl1, entry1, entry2);
			empl2 = addLogbookEntries(empl2, entry3);

			TaskDAO.insertTask(task2);
			TaskDAO.insertTask(issue1);

			entry1 = addTaskToLogbookEntry(entry1, task1);

			entry2 = addTaskToLogbookEntry(entry2, issue1);

			printTaskOfLogbookEntry(entry1);
			printTaskOfLogbookEntry(entry2);

			System.out.println("----- attachUserStoryToBacklog -----");
			BacklogDAO.attachUserStoryToBacklog(backlog1, userStory1, userStory2);
			BacklogDAO.listUserStoriesOfBacklog(backlog1);

			System.out.println("----- dettachUserStoryFromBacklog -----");
			BacklogDAO.detachUserStoryFromBacklog(backlog1, userStory1);
			BacklogDAO.listUserStoriesOfBacklog(backlog1);


			System.out.println("----- deleteEmployee -----");
			EmployeeDAO.deleteEmployee(empl3);

			System.out.println("----- listEmployees -----");
			listEmployees();
*/
		} finally {
			//JpaUtil.closeEntityManagerFactory();
		}
	}
}