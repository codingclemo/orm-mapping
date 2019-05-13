package swt6.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserStory.class)
public abstract class UserStory_ {

	public static volatile SingularAttribute<UserStory, Backlog> backlog;
	public static volatile SingularAttribute<UserStory, Sprint> sprint;
	public static volatile SingularAttribute<UserStory, Long> estimate;
	public static volatile SingularAttribute<UserStory, String> description;
	public static volatile SingularAttribute<UserStory, Long> id;
	public static volatile SingularAttribute<UserStory, String> title;
	public static volatile SetAttribute<UserStory, Task> tasks;

	public static final String BACKLOG = "backlog";
	public static final String SPRINT = "sprint";
	public static final String ESTIMATE = "estimate";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String TASKS = "tasks";

}

