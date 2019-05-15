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
import java.util.Collection;
import java.util.HashSet;

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

            TypedQuery<Employee> qry = em.createQuery(
                    "SELECT e FROM Employee e JOIN e.projects p WHERE p.id = :id",
                    Employee.class);
            qry.setParameter("id", project.getId());
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


    public static Collection<Project> getProjects() {
        Collection<Project> projects = new HashSet<>();
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            TypedQuery<Project> qry = em.createQuery(
                    "from Project",
                    Project.class);
            projects.addAll(qry.getResultList());
            JpaUtil.commit();
        } catch (Exception e){
            JpaUtil.rollback();
            throw e;
        }
        return projects;
    }
}
