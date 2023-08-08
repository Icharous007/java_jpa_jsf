package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.mchange.util.impl.FSMessageLoggerAdapter;

import dao.GenericDao;
import entity.Lancamento;
import repository.IDAOLancamentoImp;

@ViewScoped
@Named(value = "relatorioLancamento")
public class RelatorioLancamentosBean implements Serializable{

	private static final long serialVersionUID = -7871375362757294673L;
	
	private List<Lancamento> listaDeLancamentos = new ArrayList<>();
	
	private GenericDao<Lancamento> dao = new GenericDao<>();
	
	private IDAOLancamentoImp lancamentoImp = new IDAOLancamentoImp();
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private String numeroDaNota;

	public List<Lancamento> getListaDeLancamentos() {
		return listaDeLancamentos;
	}

	public void setListaDeLancamentos(List<Lancamento> listaDeLancamentos) {
		this.listaDeLancamentos = listaDeLancamentos;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNumeroDaNota() {
		return numeroDaNota;
	}

	public void setNumeroDaNota(String numeroDaNota) {
		this.numeroDaNota = numeroDaNota;
	}
	
	public void buscarLancamento() {
		if(dataFinal==null && dataInicial==null && numeroDaNota== null) {
			
			listaDeLancamentos = dao.getListEntity(Lancamento.class);
			
		}else if(dataFinal== null && dataInicial!= null && (numeroDaNota==null || (numeroDaNota!=null && numeroDaNota.isEmpty()))) {
			
			listaDeLancamentos = lancamentoImp.consultaLancamentosPorDataInicial(dataInicial);
			
		}else if(dataFinal!= null && dataInicial== null && (numeroDaNota==null || (numeroDaNota!=null && numeroDaNota.isEmpty()))) {
			
			listaDeLancamentos = lancamentoImp.consultaLancamentosPorDataFinal(dataFinal);
			
		}else if(dataFinal!= null && dataInicial!= null && (numeroDaNota==null || (numeroDaNota!=null && numeroDaNota.isEmpty()))) {
			if(dataFinal.after(dataInicial)) {
				
				listaDeLancamentos = lancamentoImp.consultaLancamentosPorDataInicialFinal(dataInicial, dataFinal);
			}else {
				mensagem("Data Final menor que Data Incial.");
			}
		}
		else if(numeroDaNota!= null && dataFinal==null && dataInicial==null ) {
			
			listaDeLancamentos = lancamentoImp.consultaLancamentosPorNumeroNota(numeroDaNota);
		}else if(numeroDaNota!=null && dataFinal!= null && dataInicial != null) {
			
			mensagem("Escolha um do tipos de procura ou por Data ou por Numero de NotaFiscal.");
		}
	}
	private void mensagem(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}
}
