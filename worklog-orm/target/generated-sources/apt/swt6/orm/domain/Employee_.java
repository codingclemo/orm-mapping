package swt6.orm.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SetAttribute<Employee, LogbookEntry> logbookEntries;
	public static volatile SingularAttribute<Employee, Address> address;
	public static volatile SetAttribute<Employee, Project> projects;
	public static volatile SingularAttribute<Employee, String> FirstName;
	public static volatile SingularAttribute<Employee, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, String> LastName;

	public static final String LOGBOOK_ENTRIES = "logbookEntries";
	public static final String ADDRESS = "address";
	public static final String PROJECTS = "projects";
	public static final String FIRST_NAME = "FirstName";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	public static final String ID = "id";
	public static final String LAST_NAME = "LastName";

}

