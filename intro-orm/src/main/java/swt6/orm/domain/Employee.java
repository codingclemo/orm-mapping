package swt6.orm.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;

@Entity

public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String FirstName;
    private String LastName;

    //@Transient
    private LocalDate dateOfBirth;

    //alt + einfügen

    // classes that are persisted by hibernate must have a default constructor
    public Employee(){
        //nothing here
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        FirstName = firstName;
        LastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
