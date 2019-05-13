package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserStory implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String description;
    private long estimate;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.SELECT)
    private Backlog backlog;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.SELECT)
    private Sprint sprint;

    @OneToMany(
            mappedBy = "userStory",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
    private Set<Task> tasks = new HashSet<>();

    public UserStory() {}

    public UserStory(String title, String description, long estimate) {
        this.title = title;
        this.description = description;
        this.estimate = estimate;
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

    public Backlog getBacklog() {
        return backlog;
    }

    public void setBacklog(Backlog backlog) {
        this.backlog = backlog;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", estimate=" + estimate +
                ", backlog=" + backlog +
                ", sprint=" + sprint +
                '}';
    }

    public void addTask(Task task) {
        if (tasks == null) {
            throw new IllegalArgumentException("sprints is null");
        }
        task.attachUserStory(this);
        this.tasks.add(task);
    }
}
