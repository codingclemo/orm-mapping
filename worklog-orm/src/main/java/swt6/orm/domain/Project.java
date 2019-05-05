package swt6.orm.domain;

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

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  // Define how join table looks like
  @JoinTable( name = "PROJECT_EMPLOYEE",
          joinColumns = @JoinColumn(name = "PROJECT_ID"),
          inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
  private Set<Employee> members = new HashSet<>();

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
}
