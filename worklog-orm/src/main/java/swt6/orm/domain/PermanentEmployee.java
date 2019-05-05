package swt6.orm.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
// Version 1: Needed for InheritanceType.SINGLE_TABLE only
//@DiscriminatorValue("P") //not needed in Version 2
public class PermanentEmployee extends Employee {
  private static final long serialVersionUID = 1L;
  private double salary;

  public PermanentEmployee() {  
  }
  
  public PermanentEmployee(String firstName, String lastName, LocalDate dateOfBirth) {
    super(firstName, lastName, dateOfBirth);
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }
  
  public String toString() {
    return super.toString() + ", salary=" + salary;
  }
}
