import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteJPA {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("cusro-jpa-jsf");
		
		EntityManager teste = factory.createEntityManager();
		teste.isOpen();
		factory.close();
		
	}
}
