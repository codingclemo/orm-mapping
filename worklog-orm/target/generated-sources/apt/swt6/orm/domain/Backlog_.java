package swt6.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Backlog.class)
public abstract class Backlog_ {

	public static volatile SingularAttribute<Backlog, String> vision;
	public static volatile SingularAttribute<Backlog, String> description;
	public static volatile SingularAttribute<Backlog, Project> project;
	public static volatile SetAttribute<Backlog, UserStory> userStories;
	public static volatile SingularAttribute<Backlog, Long> id;

	public static final String VISION = "vision";
	public static final String DESCRIPTION = "description";
	public static final String PROJECT = "project";
	public static final String USER_STORIES = "userStories";
	public static final String ID = "id";

}

