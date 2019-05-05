package swt6.orm.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TemporaryEmployee.class)
public abstract class TemporaryEmployee_ extends swt6.orm.domain.Employee_ {

	public static volatile SingularAttribute<TemporaryEmployee, LocalDate> endDate;
	public static volatile SingularAttribute<TemporaryEmployee, Double> hourlyRate;
	public static volatile SingularAttribute<TemporaryEmployee, String> renter;
	public static volatile SingularAttribute<TemporaryEmployee, LocalDate> startDate;

	public static final String END_DATE = "endDate";
	public static final String HOURLY_RATE = "hourlyRate";
	public static final String RENTER = "renter";
	public static final String START_DATE = "startDate";

}

