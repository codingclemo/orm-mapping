package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long     id;
  private String   name;

  @ManyToMany(
          cascade = CascadeType.ALL,
          fetch = FetchType.EAGER)
  // Define how join table looks like
  @JoinTable( name = "PROJECT_EMPLOYEE",
          joinColumns = @JoinColumn(name = "PROJECT_ID"),
          inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
  private Set<Employee> members = new HashSet<>();


  @OneToMany(
          mappedBy = "project",
          cascade = {CascadeType.PERSIST, CascadeType.MERGE},
          fetch = FetchType.EAGER,
          orphanRemoval = true
  )
  @Fetch(FetchMode.SELECT)
  private Set<Sprint> sprints = new HashSet<>();

  //@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OneToOne( cascade = CascadeType.ALL)
  @JoinColumn(name = "BACKLOG_ID")
  private Backlog backlog;

  public Long getId() {
    return id;
  }

  public Project() {  
  }
  
  public Project(String name) {
    this.name = name;  
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }

  public Set<Employee> getMembers() {
    return members;
  }

  public void setMembers(Set<Employee> members) {
    this.members = members;
  }

  public void addMember( Employee empl) {
    if (empl == null) {
      throw new IllegalArgumentException("Null Employee");
    }
    empl.getProjects().add(this);
    this.members.add(empl);
  }

  public Set<Sprint> getSprints() {
    return sprints;
  }

  public void setSprints(Set<Sprint> sprints) {
    this.sprints = sprints;
  }

  public void addSprint( Sprint sprint) {
    if (sprints == null) {
      throw new IllegalArgumentException("sprints is null");
    }
    sprint.attachProject(this);
    this.sprints.add(sprint);
  }

  public Backlog getBacklog() {
    return backlog;
  }

  public void setBacklog(Backlog backlog) {
    this.backlog = backlog;
  }
}
