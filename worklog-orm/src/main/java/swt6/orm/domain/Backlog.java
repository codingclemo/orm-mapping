package swt6.orm.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Backlog implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String vision;
    private String description;

    /*
    @OneToOne(
            fetch = FetchType.LAZY
    )
    @MapsId // maps id with parent project
    */
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @OneToMany(
            mappedBy = "backlog",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.SELECT)
    private Set<UserStory> userStories = new HashSet<>();

    public Backlog() {}

    public Backlog(String vision, String description) {
        super();
        this.vision = vision;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(Set<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addStory(UserStory story) {
        if (userStories == null) {
            throw new IllegalArgumentException("userStories is null");
        }
        story.setBacklog(this);
        userStories.add(story);
    }

    @Override
    public String toString() {
        return "Backlog{" +
                "id=" + id +
                ", vision='" + vision + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                '}';
    }
}
