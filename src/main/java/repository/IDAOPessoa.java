package repository;

import java.util.List;

import javax.faces.model.SelectItem;

import entity.Pessoa;

public interface IDAOPessoa {
	Pessoa consultarUsuario(String login, String senha);
	
	List<SelectItem> listaEstados();
}
