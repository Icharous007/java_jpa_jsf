package repository;

import java.io.Serializable;
import java.util.Date;
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


	@Override
	public List<Lancamento> consultaLancamentosPorNumeroNota(String numeroNota) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT lanc FROM Lancamento lanc");
		sql.append(" WHERE lanc.numeroNotaFiscal='").append(numeroNota.trim()).append("'");
		retorno = entityManager.createQuery(sql.toString())
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
		
	}


	@Override
	public List<Lancamento> consultaLancamentosPorDataInicial(Date dataInicial) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		retorno = entityManager.createQuery("FROM Lancamento where dataInicial >= :data_inicial ORDER BY id desc", Lancamento.class)
				.setParameter("data_inicial", dataInicial)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}


	@Override
	public List<Lancamento> consultaLancamentosPorDataFinal(Date dataFinal) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		retorno = entityManager.createQuery("FROM Lancamento where dataFinal <= :data_final ORDER BY id desc", Lancamento.class)
				.setParameter("data_final", dataFinal)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}


	@Override
	public List<Lancamento> consultaLancamentosPorDataInicialFinal(Date dataInical, Date dataFinal) {
		List<Lancamento> retorno = null;
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		retorno = entityManager.createQuery("FROM Lancamento where dataFinal <= :data_final and dataInicial >= :data_inicial ORDER BY id desc", Lancamento.class)
				.setParameter("data_final", dataFinal)
				.setParameter("data_inicial", dataInical)
				.getResultList();
		entityTransaction.commit();
		entityManager.close();
		return retorno;
	}

}
