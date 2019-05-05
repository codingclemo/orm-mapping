package swt6.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sprint.class)
public abstract class Sprint_ {

	public static volatile SingularAttribute<Sprint, LocalDateTime> endDate;
	public static volatile SingularAttribute<Sprint, Project> project;
	public static volatile SetAttribute<Sprint, UserStory> userStories;
	public static volatile SingularAttribute<Sprint, Long> id;
	public static volatile SingularAttribute<Sprint, LocalDateTime> startDate;

	public static final String END_DATE = "endDate";
	public static final String PROJECT = "project";
	public static final String USER_STORIES = "userStories";
	public static final String ID = "id";
	public static final String START_DATE = "startDate";

}

