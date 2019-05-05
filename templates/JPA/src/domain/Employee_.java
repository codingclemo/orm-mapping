package swt6.orm.domain;

import java.time.LocalDate;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Employee.class)
public class Employee_ {
	public static volatile SingularAttribute<Employee, Long>		  id;
	public static volatile SingularAttribute<Employee, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<Employee, String>	  firstName;
	public static volatile SingularAttribute<Employee, String>	  lastName;
	public static volatile SingularAttribute<Employee, Address>	  address;
	public static volatile SetAttribute<Employee, LogbookEntry>	  logbookEntries;
	public static volatile ListAttribute<Employee, IntAddress>	  secondaryAddresses;
}
