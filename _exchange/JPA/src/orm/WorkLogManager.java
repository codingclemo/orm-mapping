package swt6.orm.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import swt6.orm.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WorkLogManager {

	@SuppressWarnings("unused")
	private static void insertEmployee1(Employee empl) {
		// TODO
	}

	private static void insertEmployee(Employee empl) {
		// TODO
	}

	private static <T> void insertEntity(T entity) {
		// TODO
	}

	private static <T> T saveEntity(T entity) {
		// TODO
		return null;
	}

	private static void listEmployees() {
		// TODO
	}

	private static Employee addLogbookEntries(Employee empl, LogbookEntry... entries) {
		// TODO
		return null;
	}

	private static Employee assignProjectsToEmployee(Employee empl,
													 Project... projects) {
		// TODO
		return null;
	}

	private static void testFetchingStrategies() {
		/*
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
		*/
	}



	public static void main(String[] args) {
		try {
			/*
			System.out.println("----- create schema -----");
			JpaUtil.getEntityManagerFactory();
			*/
				
			/*	
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
			*/

			/* LogbookEntries */
			/*
			LogbookEntry entry1 = new LogbookEntry("Analyse", LocalDateTime.of(2018, 2, 1, 10, 15),
					LocalDateTime.of(2018, 2, 1, 15, 30));
			LogbookEntry entry2 = new LogbookEntry("Implementierung", LocalDateTime.of(2018, 2, 1, 8, 45),
					LocalDateTime.of(2018, 2, 1, 17, 15));
			LogbookEntry entry3 = new LogbookEntry("Testen", LocalDateTime.of(2018, 2, 1, 12, 30),
					LocalDateTime.of(2018, 2, 1, 17, 0));
			*/

			/*
			System.out.println("------ insert entity -------");
			insertEntity(empl1);
			*/

			/*
			System.out.println("------ save entity -------");
			saveEntity(empl2);
			*/
				
			/*
			System.out.println("------ list employees -------");
			listEmployees();
			*/
				
			/*
			System.out.println("----- addLogbookEntries -----");
			empl1 = addLogbookEntries(empl1, entry1, entry2);
			empl2 = addLogbookEntries(empl2, entry3);
			*/
				
			/*
			Project p1 = new Project("Office");
			Project p2 = new Project("Enterprise Server");
			*/
			
			/*
			System.out.println("----- assignProjectsToEmployees -----");
			empl1 = assignProjectsToEmployee(empl1, p1, p2);
			empl2 = assignProjectsToEmployee(empl2, p2);
			*/

			/*
			System.out.println("----- listEmployees -----");
			listEmployees();
			*/	
			
			/*
			System.out.println("----- testFetchingStrategies ------");
			testFetchingStrategies();
			*/

			/*
			System.out.println("----- listLogbookEntriesOfEmployee -----");
			listEntriesOfEmployee(empl1);
			*/

			/*
			System.out.println("----- listEmployeesResidingIn -----");
			listEmployeesResidingIn( "4232" );
			*/

			/*
			System.out.println("----- loadEmployeesWithEntries -----");
			loadEmployeesWithEntries();
			*/

			/*
			System.out.println("----- listEntriesOfEmployeeCQ -----");
			listEntriesOfEmployeeCQ(empl1);
			*/

		} finally {
			//JpaUtil.closeEntityManagerFactory();
		}
	}
}