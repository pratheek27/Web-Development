package swe4;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Entity;
/**
* 
*/
public class SaveData {

	@PersistenceContext
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("student");
	EntityManager em;
	public static EntityManager getEntityManager() {
		EntityManager entityManager = emf.createEntityManager();
		return entityManager;
	}
	public SaveData() {
		super();
	}
	public static void studentsData(Student student) {

		EntityManager etm = getEntityManager();
		etm.getTransaction().begin();
		etm.persist(student);
		etm.getTransaction().commit();
		etm.close();

	}

}
