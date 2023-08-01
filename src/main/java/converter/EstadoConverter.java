package converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Estados;
import jpautil.JPAUTIL;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Serializable, Converter{

	private static final long serialVersionUID = 5191639376275487539L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		Long codigo;
		try {
			codigo = Long.parseLong(codigoEstado);
		} catch (Exception e) {
			return null;
		}
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Estados estado = entityManager.find(Estados.class, codigo);
		return estado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		if(estado == null) {
			return null;
		}
		if(estado instanceof Estados) {
			return ((Estados) estado).getId().toString();			
		}else {
			return estado.toString();
		}
	}

}
