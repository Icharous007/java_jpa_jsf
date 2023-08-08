package repository;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import entity.Pessoa;

public interface IDAOPessoa {
	Pessoa consultarUsuario(String login, String senha);
	
	List<SelectItem> listaEstados();

	List<Pessoa> relatorioPessoa(String nome, Date dataDeNascimento, String perfil, String sexo, Integer[] linguagens);
}
