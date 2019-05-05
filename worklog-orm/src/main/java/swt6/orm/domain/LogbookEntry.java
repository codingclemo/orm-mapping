package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class LogbookEntry {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER, optional = false)
    @Fetch(FetchMode.SELECT) // VERSION2: Fetchmodes
    private Employee employee;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "TASK_ID")
    private Task task;

    public LogbookEntry() {}

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void attachEmployee(Employee employee) {
        if ( this.employee != null) {
            this.employee.getLogbookEntries().remove( this );
        }

        //add bidirectional link
        if ( employee != null) {
            employee.getLogbookEntries().add( this );
        }
        this.employee = employee;
    }

    public void detachEmployee() {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove( this );
        }
        this.employee = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "LogbookEntry{" +
                "id=" + id +
                ", activity='" + activity + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", employee=" + employee +
                '}';
    }
}