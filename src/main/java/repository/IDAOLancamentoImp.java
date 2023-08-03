package repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Lancamento;
import jpautil.JPAUTIL;

public class IDAOLancamentoImp implements IDAOLancameto, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Lancamento> consultar(Long idUser) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		retorno = entityManager.createQuery("FROM Lancamento where usuariologado_id = :login_user", Lancamento.class)
				.setParameter("login_user", idUser)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}
	

	@Override
	public List<Lancamento> consultar3PorPagina(Long idUser) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		retorno = entityManager.createQuery("FROM Lancamento where usuariologado_id = :login_user ORDER BY id desc", Lancamento.class)
				.setParameter("login_user", idUser)
				.setMaxResults(3)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}

}
