package swt6.orm.dao;

import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.LogbookEntry_;
import swt6.orm.domain.Project;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeDAO extends BaseDAO {

    public static void insertEmployee(Employee employee) {
        insertEntity(employee);
    }

    public static Employee updateEmployee(Employee employee) {
        return saveEntity(employee);
    }

    public static void deleteEmployee(Employee employee) {
        deleteEntity(employee);
    }

    public static Employee find(long employeeId) {
        Employee empl;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            empl = em.find(Employee.class, employeeId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return empl;
    }
    /*
        public static void deleteEmployee(Employee employee) {
            try {
                EntityManager em = JpaUtil.getTransactedEntityManager();

               Query qry = em.createQuery(
                        "delete from Employee e where ID = :emplID ",
                        Employee.class);
                qry.setParameter("emplID", employee.getId());
                qry.executeUpdate();
            }catch (Exception e){
                JpaUtil.rollback();
                throw e;
            }
        }
    */
    public static void listEmployees() {
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

    public static Employee addLogbookEntries(Employee empl, LogbookEntry... entries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            for (LogbookEntry entry : entries) {
                //entry = em.merge(entry);
                //Version 1: unidirectional
                //empl.getLogbookEntries().add( entry );
                //Version 2: bidirectional
                empl.addLogbookEntry( entry );
                entry.setEmployee(empl);
            }
            empl = em.merge(empl);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return empl;
    }

    public static Employee assignProjectsToEmployee(Employee empl,
                                                     Project... projects) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            for (Project project : projects) {
                project = em.merge(project);
                empl.addProjects(project);
                project.addMember(empl);
            }
            empl = em.merge(empl);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return empl;
    }

    public static void listEntriesOfEmployee(Employee empl1) {
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

    public static void loadEmployeesWithEntries() {
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

    public static void listEntriesOfEmployeeCQ(Employee empl1) {
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

    public static void listProjects(Employee empl) {
        EntityManager em = JpaUtil.getTransactedEntityManager();
        try {
            TypedQuery<Project> qry = em.createQuery(
                    "from Project where employee = :empl ",
                    Project.class);
            qry.setParameter("empl", empl);

            qry.getResultList().forEach(entry -> System.out.println( entry ));

            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }
}
