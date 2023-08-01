package entity;

import java.io.Serializable;

public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String tipo;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Usuario(String n, String t) {
		this.nome = n;
		this.tipo = t;
	}
}
