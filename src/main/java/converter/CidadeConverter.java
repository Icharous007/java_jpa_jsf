package converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import entity.Cidades;
import jpautil.JPAUTIL;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Serializable, Converter{

	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		Long codigo;
		try {
			codigo = Long.parseLong(id);
		} catch (Exception e) {
			return null;
		}
		EntityManager entityManager = JPAUTIL.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		Cidades cidade = entityManager.find(Cidades.class, codigo);
		entityTransaction.commit();
		entityManager.close();
		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {
		if(cidade==null) {
			return null;
		}
		if(cidade instanceof Cidades) {
			
			return ((Cidades) cidade).getId().toString();
		}else {
			return cidade.toString();
		}
	}

}
/*o 
 * 
 * https://rponte.com.br/2008/07/26/entity-converters-pra-da-e-vender/
 * cara, o ideal seria fazer com Conversor, afinal a função dele é justamente essa, converter objeto.
Quando funcionar com o conversor, tbm irá funcionar para ID e vice-versa.
Então, temos que descobrir o que está acontecendo pra ele perder os valores no submit.

Para vermos se tem algo relacionado ao escopo, tira o @RequestScoped e coloca @ViewScoped.
e implementa Serializable nas suas entidades (Usuario, Endereco, Cidade) etc.
se isso funcionar podemos tirar algumas conclusões.
 * @ViewScoped guarda o estado dos atributos durante todas as submissões para mesma tela.

quando você trabalha com @RequestScoped e faz requisições Ajax, você acaba perdendo o estado dos atributos, pq o ManagedBean é instanciado novamente e o máximo que vc vai ter são os atributos que foram submetidos nessa requisição Ajax específica. Como vc usou execute="@this" provavelmente só setou nessa nova instância os atributos do estado e não da cidade.
Depois vc tenta colocar execute="@form", é capaz que funcione mesmo com @RequestScoped pq neste caso você levará para esse novo request todos os atributos setados.

Sobre manter as coleções de Estado e Cidade, acho que não é necessário ficar solicitando ao DAO sempre, pq normalmente isso é uma coisa que não muda muito.
a não ser que o cadastro de estado/cidade seja uma coisa que é alterada no sistema com muita frequência.

existem algumas abordagens interessantes, como por exemplo criar um EstadoBean e CidadeBean com @ApplicationScoped
além de fazer o select no banco só uma vez, você pode usar os métodos #{estadoBean.getEstados} e #{cidadeBean.getCidades} diversas vezes, e em diversas telas diferentes, sem precisar ficar criando um getCidades() pra cada ManagedBean que vc for criar.
 * */
