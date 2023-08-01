package repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Estados;
import entity.Pessoa;
import jpautil.JPAUTIL;

public class IDAOPessoaImp implements IDAOPessoa {

	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Pessoa pessoa = null;
		try {
		 pessoa = entityManager.createQuery("FROM Pessoa where login = :login_user and senha = :senha_user", Pessoa.class)
				.setParameter("login_user", login)
				.setParameter("senha_user", senha)
				.getSingleResult();
			
		} catch (Exception e) {
			return null;
		}
		
		transaction.commit();
		entityManager.close();
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {
		List<SelectItem>listaDeItens = new ArrayList<>();
		
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<Estados> estados = (List<Estados>) entityManager.createQuery("FROM Estados").getResultList();
		for (Estados estado: estados) {
			listaDeItens.add(new SelectItem(estado,estado.getNome()));
		}
		return listaDeItens;
	}
	
	

}
