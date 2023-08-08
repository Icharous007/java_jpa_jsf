package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import dao.GenericDao;
import entity.Lancamento;
import entity.Pessoa;
import repository.IDAOLancamentoImp;

@Named
@RequestScoped
public class LancamentoBean implements Serializable{

	private static final long serialVersionUID = 6543321998506355180L;
	private Lancamento lancamento = new Lancamento();
	private GenericDao<Lancamento> dao = new GenericDao<>();
	private List<Lancamento> lancamentos = new ArrayList<>();
	private IDAOLancamentoImp idaoLancamentoImp = new IDAOLancamentoImp();
	
	public String salvar() {
		//recuperando o usuario logado
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa usuarioLogado = (Pessoa) externalContext.getSessionMap().get("logedUser");
		lancamento.setUsuarioLogado(usuarioLogado);
		if(lancamento.getDataFinal().before(lancamento.getDataInicial())) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Data Iniciol maior que data fim.");
			facesContext.addMessage(null, facesMessage);
			
		}
		dao.salvar(lancamento);
		
		carregarLancamento();
		
		return "";
	}
	@PostConstruct
	private void carregarLancamento() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa usuarioLogado = (Pessoa) externalContext.getSessionMap().get("logedUser");
		lancamentos = idaoLancamentoImp.consultar3PorPagina(usuarioLogado.getId());
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	public String novo() {
		lancamento = new Lancamento();
		return "";
	}
	public String merge() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa usuarioLogado = (Pessoa) externalContext.getSessionMap().get("logedUser");
		lancamento.setUsuarioLogado(usuarioLogado);
		lancamento = dao.merge(lancamento);
		carregarLancamento();
		return "";
	}
	public String delete() {
		dao.delete(lancamento);
		lancamento = new Lancamento();
		carregarLancamento();
		return "";
	}

}
