package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sprint implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER, optional = false
    )
    @Fetch(FetchMode.SELECT)
    private Project project;

    @OneToMany(
            mappedBy = "sprint",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.SELECT)
    private Set<UserStory> userStories = new HashSet<>();

    public Sprint() {}

    public Sprint(LocalDateTime startDate, LocalDateTime endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void attachProject(Project project) {
        if (this.project != null) {
            this.project.getSprints().remove(this);
        }
        if (project != null) {
            project.getSprints().add(this);
        }
        this.project = project;
    }

    public void detachProject() {
        if (this.project != null) {
            this.project.getSprints().remove(this);
        }
        this.project = null;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", project=" + project +
                '}';
    }

    public void addStory(UserStory story) {
        if (userStories == null) {
            throw new IllegalArgumentException("userStories is null");
        }
        story.setSprint(this);
        userStories.add(story);
    }
}
