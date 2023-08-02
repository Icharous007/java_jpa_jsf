package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;


@Entity
public class Pessoa implements Serializable	{

	private static final long serialVersionUID = 7052043208745875658L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	//@NotEmpty(message = "sobrenome está vazio.")
	//@NotEmpty(message = "sobrenome esta vazio")
	private String sobrenome;
	private Integer idade;
	
	@Temporal(TemporalType.DATE)
	private Date dataDeNascimento = new Date();
	
	private String sexo;
	
	private String[] frameworks;
	
	@Column(columnDefinition="boolean default false")
	private boolean ativo;
	
	@Column(unique = true)
	private String login;
	
	private String senha;
	
	private String perfil;	
	
	private String nivel;
	
	private Integer[] linguagens;
	
	private String cep;
	
	private String logradouro;
	
	private String complemento;
	
	private String bairro;
	
	private String localidade;
	
	private String uf;
	
	private String ibge;
	
	private String gia;
	
	private String ddd;
	
	private String siafi;
	
	//mantendo o objeto em memória 
	//não o deixa em persisténcia
	
	@Transient
	private Estados estados;
	
	@ManyToOne
	private Cidades cidade;
	
	@Column(columnDefinition = "text")
	private String fotoMiniaturaBase64;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoBase64;
	
	private String fotoExtensao;
	
	public byte[] getFotoBase64() {
		return fotoBase64;
	}

	public void setFotoBase64(byte[] fotoBase64) {
		this.fotoBase64 = fotoBase64;
	}

	public String getFotoExtensao() {
		return fotoExtensao;
	}

	public void setFotoExtensao(String fotoExtensao) {
		this.fotoExtensao = fotoExtensao;
	}

	public String getFotoMiniaturaBase64() {
		return fotoMiniaturaBase64;
	}

	public void setFotoMiniaturaBase64(String fotoMiniaturaBase64) {
		this.fotoMiniaturaBase64 = fotoMiniaturaBase64;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getIbge() {
		return ibge;
	}

	public void setIbge(String ibge) {
		this.ibge = ibge;
	}

	public String getGia() {
		return gia;
	}

	public void setGia(String gia) {
		this.gia = gia;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getSiafi() {
		return siafi;
	}

	public void setSiafi(String siafi) {
		this.siafi = siafi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}
	
	public boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public Integer[] getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	public Cidades getCidade() {
		return cidade;
	}

	public void setCidade(Cidades cidade) {
		this.cidade = cidade;
	}
}
