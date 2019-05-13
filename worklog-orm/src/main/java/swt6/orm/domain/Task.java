package swt6.orm.domain;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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


    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private UserStory userStory;

    @OneToMany(
            mappedBy = "task",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @Fetch(FetchMode.SELECT)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

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

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }

    public void attachUserStory(UserStory userStory) {
        if ( this.userStory != null) {
            this.userStory.getTasks().remove( this );
        }

        //add bidirectional link
        if ( userStory != null) {
            userStory.getTasks().add( this );
        }
        this.userStory = userStory;
    }

    public void addLogbookEntry(LogbookEntry logbookEntry) {

        if (logbookEntry == null) return;
        if (logbookEntry.getTask() != null) {
            logbookEntry.getTask().getLogbookEntries().remove( logbookEntry );
        }
        this.logbookEntries.add(logbookEntry);
        logbookEntry.setTask(this);
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

