package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Task implements Serializable {

    public enum Status {
        open, in_progress, done
    }

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;
    private long estimate;
    private Status status;


    //private UserStory userStory;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "LOGBOOKENTRY_ID")
    private LogbookEntry logbookEntry;


    public Task() {}

    public Task(String title, String description, long estimate, Status status) {
        this.title = title;
        this.description = description;
        this.estimate = estimate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEstimate() {
        return estimate;
    }

    public void setEstimate(long estimate) {
        this.estimate = estimate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LogbookEntry getLogbookEntry() {
        return logbookEntry;
    }

    public void setLogbookEntry(LogbookEntry logbookEntry) {
        this.logbookEntry = logbookEntry;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", estimate=" + estimate +
                ", status=" + status +
                '}';
    }
}

