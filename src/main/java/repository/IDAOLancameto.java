package repository;

import java.util.Date;
import java.util.List;

import entity.Lancamento;

public interface IDAOLancameto {
	
	List<Lancamento> consultar (Long idUser);
	
	List<Lancamento> consultar3PorPagina(Long idUser);
	
	List<Lancamento> consultaLancamentosPorNumeroNota(String numeroNota);
	
	List<Lancamento> consultaLancamentosPorDataInicial(Date dataInicial);
	
	List<Lancamento> consultaLancamentosPorDataFinal(Date dataFinal);
	
	List<Lancamento> consultaLancamentosPorDataInicialFinal(Date dataInical,Date dataFinal);

}
