package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
//Version 1:
//@Inheritance (  strategy = InheritanceType.SINGLE_TABLE)
//Version 2:
//@Inheritance (  strategy = InheritanceType.TABLE_PER_CLASS)
//Version 3:
@Inheritance( strategy = InheritanceType.JOINED)
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String FirstName;
    private String LastName;
    //@Transient
    private LocalDate dateOfBirth;

    //alt + einfügen

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true) //orphanRemoval...wenn Employee gelöscht wird, werden auch dazugehörige logbookentires gelöscht
    //@Fetch( FetchMode.JOIN ) //Version1: Fetchmodes
    @Fetch( FetchMode.SELECT ) //Version2: Fetchmodes
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    //Version 2:
    //@Embedded
    //Version 3:
    @OneToOne( cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "ADDRESS_ID") // set name to join column
    private Address address;

    @ManyToMany(
            cascade = CascadeType.ALL,
            mappedBy = "members" ,
            fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();


    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

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


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().getLogbookEntries().remove( entry );
        }
        // set bidirection link
        this.logbookEntries.add(entry);
        entry.setEmployee( this );
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


    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProjects( Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project is null");
        }
        project.getMembers().add(this);
        this.projects.add(project);
    }
}
