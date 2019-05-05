package swt6.orm.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LogbookEntry.class)
public abstract class LogbookEntry_ {

	public static volatile SingularAttribute<LogbookEntry, String> activity;
	public static volatile SingularAttribute<LogbookEntry, LocalDateTime> startTime;
	public static volatile SingularAttribute<LogbookEntry, Long> id;
	public static volatile SingularAttribute<LogbookEntry, LocalDateTime> endTime;
	public static volatile SingularAttribute<LogbookEntry, Employee> employee;

	public static final String ACTIVITY = "activity";
	public static final String START_TIME = "startTime";
	public static final String ID = "id";
	public static final String END_TIME = "endTime";
	public static final String EMPLOYEE = "employee";

}

