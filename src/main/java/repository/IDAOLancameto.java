package repository;

import java.util.List;

import entity.Lancamento;

public interface IDAOLancameto {
	
	List<Lancamento> consultar (Long idUser);
	
	List<Lancamento> consultar3PorPagina(Long idUser);

}
