package swt6.orm.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Project.class)
public abstract class Project_ {

	public static volatile SetAttribute<Project, Employee> members;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, Long> id;

	public static final String MEMBERS = "members";
	public static final String NAME = "name";
	public static final String ID = "id";

}

