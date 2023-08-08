package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import dao.GenericDao;
import entity.Pessoa;
import repository.IDAOPessoa;
import repository.IDAOPessoaImp;

@Named(value = "relatorioUsuarioBean")
@ViewScoped
public class RelatorioUsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Pessoa> listaDeUsuarios = new ArrayList<>();
	
	private IDAOPessoa idaoPessoa = new IDAOPessoaImp();
	
	private GenericDao<Pessoa> genericDao = new GenericDao<>();
	
	private String nome;
	private Date dataDeNascimento;
	private String perfil;
	private String sexo;
	private Integer[] linguagens; 
	
	public List<Pessoa> getListaDeUsuarios() {
		return listaDeUsuarios;
	}
	public void setListaDeUsuarios(List<Pessoa> listaDeUsuarios) {
		this.listaDeUsuarios = listaDeUsuarios;
	}
	public IDAOPessoa getIdaoPessoa() {
		return idaoPessoa;
	}
	public void setIdaoPessoa(IDAOPessoa idaoPessoa) {
		this.idaoPessoa = idaoPessoa;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer[] getLinguagens() {
		return linguagens;
	}
	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}	
	
	public void buscarUsuarios() {
		if(nome==null && dataDeNascimento ==null && perfil == null&& sexo==null&&linguagens==null) {
			listaDeUsuarios = genericDao.getListEntity(Pessoa.class);
			
		}else {
			listaDeUsuarios = idaoPessoa.relatorioPessoa(nome,dataDeNascimento,perfil,sexo,linguagens);
		}
	}
}
