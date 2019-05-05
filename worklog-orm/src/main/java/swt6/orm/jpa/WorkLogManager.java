package swt6.orm.jpa;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;


import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorkLogManager {

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

	private static <T> void insertEntity(T entity) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			em.persist(entity);
			JpaUtil.commit();

		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

	private static <T> T saveEntity(T entity) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			entity = em.merge(entity);
			JpaUtil.commit();

		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return entity;
	}

	private static void listEmployees() {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			List<Employee> emplList = em.createQuery("select e from Employee e", Employee.class).getResultList();
			emplList.forEach(e-> {
				System.out.println(e);
			});
		}catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

	private static Employee addLogbookEntries(Employee empl, LogbookEntry... entries) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			for (LogbookEntry entry : entries) {
				//Version 1: unidirectional
				//empl.getLogbookEntries().add( entry );
				//Version 2: bidirectional
				empl.addLogbookEntry( entry );
			}
			empl = em.merge(empl);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return empl;
	}

	private static Employee assignProjectsToEmployee(Employee empl,
													 Project... projects) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			empl = em.merge(empl);
			for (	Project project : projects) {
				empl.addProjects(project);
				em.merge(project);
				// Version 2: project.addMember(empl)
			}
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return empl;
	}

	private static void listEntriesOfEmployee(Employee empl1) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			// VERSION 1:
			/*
			TypedQuery<LogbookEntry> qry = em.createQuery(
					"from LogbookEntry where employee.id = " + empl1.getId(),
					LogbookEntry.class);
			*/
			//VERSION 2:
			/*
			TypedQuery<LogbookEntry> qry = em.createQuery(
					"from LogbookEntry where employee.id = :emplId ",
					LogbookEntry.class);
			qry.setParameter("emplId", empl1.getId());
			*/
			// VERSION 3:
			//VERSION 2:
			TypedQuery<LogbookEntry> qry = em.createQuery(
					"from LogbookEntry where employee = :empl ",
					LogbookEntry.class);
			qry.setParameter("empl", empl1);

			qry.getResultList().forEach(entry -> System.out.println( entry ));

			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}


	private static void loadEmployeesWithEntries() {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			// VERSION 1:
			TypedQuery<Employee> qry = em.createQuery(
					"select distinct e from Employee  e join fetch e.logbookEntries",
					Employee.class
			);
			qry.getResultList().forEach( System.out::println );

			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}


	private static void listEntriesOfEmployeeCQ(Employee empl1) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			// VERSION 1:
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LogbookEntry> entryCQ = cb.createQuery( LogbookEntry.class);
			Root<LogbookEntry> entry = entryCQ.from(LogbookEntry.class);
			ParameterExpression<Employee> p = cb.parameter( Employee.class);

			// Query DB using DSL
			entryCQ.where( cb.equal(entry.get("employee"), p)).select( entry );
			//Typed Query with meta classes
			entryCQ.where( cb.equal(entry.get(LogbookEntry_.employee), p)).select( entry );


			TypedQuery<LogbookEntry> entryQry = em.createQuery( entryCQ );
			entryQry.setParameter( p, empl1 );
			entryQry.getResultList().forEach( System.out::println );

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



	public static void main(String[] args) {
		try {

			System.out.println("----- create schema -----");
			JpaUtil.getEntityManagerFactory();

			//Employee empl1 = new Employee("Franz", "Mayr", LocalDate.of(1980, 12, 24));
			//Employee empl2 = new Employee("Bill", "Gates", LocalDate.of(1970, 1, 21));

			//-- insertEmployee1(empl1);
			//-- insertEmployee1(empl2);

			//-- insertEmployee(empl1);
			//-- insertEmployee(empl2);

			//--insertEntity(empl1);
			//--insertEntity(empl2);

			/*
			saveEntity(empl1);
			saveEntity(empl2);

			System.out.println(empl1);
			System.out.println(empl2);
			*/
						/*
			empl1.setAddress( new Address("77777", "Redmond", "Clinton Street"));
			empl2.setAddress( new Address("666", "Hagenberg", "Gates of hell"));
			*/

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


			/* LogbookEntries */

			LogbookEntry entry1 = new LogbookEntry("Analyse", LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 1, 15, 30));
			LogbookEntry entry2 = new LogbookEntry("Implementierung", LocalDateTime.of(2018, 2, 1, 8, 45),
					LocalDateTime.of(2018, 2, 1, 17, 15));
			LogbookEntry entry3 = new LogbookEntry("Testen", LocalDateTime.of(2018, 2, 1, 12, 30),
					LocalDateTime.of(2018, 2, 1, 17, 0));



			System.out.println("------ insert entity -------");
			insertEntity(empl1);

			System.out.println("------ save entity -------");
			saveEntity(empl2);

			System.out.println("------ list employees -------");
			listEmployees();

			System.out.println("----- addLogbookEntries -----");
			empl1 = addLogbookEntries(empl1, entry1, entry2);
			empl2 = addLogbookEntries(empl2, entry3);


			Project p1 = new Project("Office");
			Project p2 = new Project("Enterprise Server");




			System.out.println("----- assignProjectsToEmployees -----");
			empl1 = assignProjectsToEmployee(empl1, p1, p2);
			empl2 = assignProjectsToEmployee(empl2, p2);


			System.out.println("----- listEmployees -----");
			listEmployees();


/*
			System.out.println("----- testFetchingStrategies ------");
			testFetchingStrategies();
*/


			System.out.println("----- listLogbookEntriesOfEmployee -----");
			listEntriesOfEmployee(empl1);


			/*
			System.out.println("----- listEmployeesResidingIn -----");
			listEmployeesResidingIn( "4232" );
			*/


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

			System.out.println("----- Backlog of project -----");
			Backlog backlog1 = new Backlog("BacklogVision","BacklogDescription");
			p1 = assignBacklogToProject(p1, backlog1);
			printBacklogOfProject(p1);


			System.out.println("----- User stories -----");
			UserStory userStory1 = new UserStory("USTitle", "USDescription", 34000);
			UserStory userStory2 = new UserStory("USTitle2", "USDescription2", 35000);
			sprint1 = assignUserStoryToSprint(sprint1, userStory1, userStory2);
			System.out.println("----- User stories of a sprint -----");
			listUserStoriesOfSprint(sprint1);

			/*
			//not working

			backlog1 = assignUserStoryToBacklog(backlog1, userStory1, userStory2);
			listUserStoriesOfBacklog(backlog1);
*/

			Task task1 = new Task("tasktitle", "taskdesc", 70000, Task.Status.open);
			Task task2 = new Task("tasktitle", "taskdesc", 70000, Task.Status.open);
			Issue issue1 = new Issue("issuetitle", "issuedesc", 70000, Task.Status.open,
					LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 14, 10, 15),
					Issue.Severity.low);

			System.out.println("----- addTaskToLogbookEntry -----");
			empl1 = addLogbookEntries(empl1, entry1, entry2);
			empl2 = addLogbookEntries(empl2, entry3);

			entry1 = addTaskToLogbookEntry(entry1, task1);

			entry2 = addTaskToLogbookEntry(entry2, issue1);

			printTaskOfLogbookEntry(entry1);
			printTaskOfLogbookEntry(entry2);

		} finally {
			//JpaUtil.closeEntityManagerFactory();
		}
	}

	private static LogbookEntry addTaskToLogbookEntry(LogbookEntry logbookEntry, Task task) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			logbookEntry = em.merge(logbookEntry);
			task = em.merge(task);
			task.setLogbookEntry(logbookEntry);
			logbookEntry.setTask(task);


			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return logbookEntry;
	}

	private static void printTaskOfLogbookEntry(LogbookEntry logbookEntry) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			TypedQuery<Task> qry = em.createQuery(
					"from Task where logbookEntry = :logbookEntry ",
					Task.class);
			qry.setParameter("logbookEntry", logbookEntry);

			qry.getResultList().forEach(entry -> System.out.println( entry ));

			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

/*	private static Project assignBacklogToProject(Project project,
												  Backlog backlog) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			backlog.setProject(project);
			backlog = em.merge(backlog);

			project.setBacklog(backlog);
			project = em.merge(project);

		//	backlog = em.merge(backlog);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return project;
	}*/

	private static Backlog assignUserStoryToBacklog(Backlog backlog, UserStory... userStories) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			for (	UserStory story : userStories) {
				story = em.merge(story);
				backlog.addStory(story);
			}
			backlog = em.merge(backlog);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return backlog;
	}

	private static void listUserStoriesOfBacklog(Backlog backlog) {
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

	private static Sprint assignUserStoryToSprint(Sprint sprint, UserStory... userStories) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			for (	UserStory story : userStories) {
				story = em.merge(story);
				sprint.addStory(story);
			}
			sprint = em.merge(sprint);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return sprint;
	}

	private static void listUserStoriesOfSprint(Sprint sprint) {
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


	private static Project assignSprintsToProject(Project project,
													 Sprint... sprints) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			for (	Sprint sprint : sprints) {
				project.addSprint(sprint);
				//em.merge(project);
			}
			project = em.merge(project);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return project;
	}

	private static void listSprintsOfProject(Project project) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			TypedQuery<Sprint> qry = em.createQuery(
					"from Sprint where project = :project ",
					Sprint.class);
			qry.setParameter("project", project);
			qry.getResultList().forEach(entry -> System.out.println( entry ));
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

	private static Project assignBacklogToProject(Project project,
												  Backlog backlog) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();
			project = em.merge(project);
			backlog = em.merge(backlog);
			backlog.setProject(project);
			project.setBacklog(backlog);
			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
		return project;
	}


	private static void printBacklogOfProject(Project project) {
		try {
			EntityManager em = JpaUtil.getTransactedEntityManager();

			TypedQuery<Backlog> qry = em.createQuery(
					"from Backlog where project = :project ",
					Backlog.class);
			qry.setParameter("project", project);

			qry.getResultList().forEach(entry -> System.out.println( entry ));

			JpaUtil.commit();
		} catch (Exception e){
			JpaUtil.rollback();
			throw e;
		}
	}

}