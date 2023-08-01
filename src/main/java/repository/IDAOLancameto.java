package repository;

import java.util.List;

import entity.Lancamento;

public interface IDAOLancameto {
	
	List<Lancamento> consultar (Long idUser);

}
