package dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jpautil.JPAUTIL;

public class GenericDao<T> implements Serializable {
	private static final long serialVersionUID = 865122424625488425L;
	
	public void salvar(T entity) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(entity);
		entityTransaction.commit();
		entityManager.close();
	}
	
	public T merge(T entity) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		T retorno = entityManager.merge(entity);
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}
	
	public void delete(T entity) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.remove(entityManager.contains(entity)? entity: entityManager.merge(entity));
		entityTransaction.commit();
		entityManager.close();
	}
	
	public void deletePorId(T entity) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Object id = JPAUTIL.getPrimaryKey(entity);
		entityManager.createQuery("DELETE FROM"+entity.getClass().getCanonicalName()+" WHERE id = "+ id).executeUpdate();
		entityTransaction.commit();
		entityManager.close();
	}
	public List<T> getListEntity(Class<T> entity){
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<T> retorno = null;
		retorno = entityManager.createQuery("FROM "+entity.getName()).getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}
	public T cosultar(Class<T> entity, String id) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		T objeto = (T) entityManager.find(entity, Long.parseLong(id));
		entityTransaction.commit();
		return objeto;
	}
}
