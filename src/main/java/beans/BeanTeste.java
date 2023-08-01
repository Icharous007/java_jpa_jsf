package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import entity.Usuario;

@Named
@RequestScoped
public class BeanTeste implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private Usuario user;
	private List<Usuario> users = new ArrayList<>();
	
	@PostConstruct
	public void iniciar() {
		users.add(new Usuario("usuario1", "tipo1"));
		users.add(new Usuario("usuario2", "tipo2"));
	}
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public List<Usuario> getUsers() {
		return users;
	}
	public void setUsers(List<Usuario> users) {
		this.users = users;
	}
	
	
}
