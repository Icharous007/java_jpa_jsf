package jpautil;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUTIL implements Serializable {

	private static final long serialVersionUID = -8042995467326641262L;
	private static EntityManagerFactory factory;
	
	static {
		if(factory==null) {
			
			factory = Persistence.createEntityManagerFactory("cusro-jpa-jsf");
		}
	}
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
