package swt6.orm.dao;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import swt6.orm.domain.Backlog;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Project;
import swt6.orm.domain.Sprint;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ProjectDAO extends BaseDAO {

    public static void insertProject(Project project) {
        insertEntity(project);
    }

    public static Project updateProject(Project project) {
        return saveEntity(project);
    }

    public static void deleteProject(Project project) {
        deleteEntity(project);
    }

    public static Project find(long projectId) {
        Project p;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            p = em.find(Project.class, projectId);
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return p;
    }

    public static Project assignBacklogToProject(Project project,
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

    public static Project assignSprintsToProject(Project project,
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

    public static void listSprintsOfProject(Project project) {
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

    public static void listMembersOfProject(Project project) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            //NativeQuery<Employee> qry = (NativeQuery) em.createNativeQuery(
            TypedQuery<Employee> qry = em.createQuery(
              "from Employee e where e.id.project = :id ", //inner join project_employee pe on pe.employee_id = e.id where pe.project_id = :id",
                    Employee.class);
            qry.setParameter("id", project.getId());
/*
            TypedQuery<Employee> qry = em.createQuery(
                    "from Employee where project = :project", //
                    Employee.class);
            qry.setParameter("project", project);
*/
            qry.getResultList().forEach(entry -> System.out.println( entry ));
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
    }

    public static void listBacklogOfProject(Project project) {
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
