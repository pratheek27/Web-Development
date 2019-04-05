package swe4;

/**
*    
* Assignment-5
*/

import java.io.Serializable;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ManagedBean
@SessionScoped
public class Searchbean implements Serializable {

	private static final long serialVersionUID = 1L;

	String firstname;
	String lastname;
	String city;
	String state;

	ArrayList<Student> studentList;
	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	/**
	 * @param studentList
	 */
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param fname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param studentid
	 */
	public void deleteRows(int sid) {

		EntityManager em = SaveData.getEntityManager();

		//Map<String, String> abc = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//String action = abc.get("student");
		Student student = em.find(Student.class, sid);
		em.getTransaction().begin();
		em.remove(student);
		em.getTransaction().commit();

	}

	/**
	 * @return
	 */
	//@SuppressWarnings("unchecked")
	public String searchStudentSurvey() {

		EntityManager em1 = SaveData.getEntityManager();

		CriteriaBuilder builder = em1.getCriteriaBuilder();
		CriteriaQuery<Student> criteria = builder.createQuery(Student.class);

		Root<Student> student = criteria.from(Student.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!(firstname.trim().equals(""))) {
			
			predicates.add(builder.equal(student.get("firstname"), getFirstname()));
		}

		if (!(lastname.trim().equals(""))) {
			
			predicates.add(builder.equal(student.get("lastname"), getLastname()));
		}

		if (!(city.trim().equals(""))) {
			
			predicates.add(builder.equal(student.get("city"), getCity()));
		}
		if (!(state.trim().equals(""))) {
			
			predicates.add(builder.equal(student.get("state"), getState()));
		}

		criteria.select(student).where(predicates.toArray(new Predicate[] {}));
		studentList = (ArrayList<Student>) em1.createQuery(criteria).getResultList();
		return "ResultLists";
	}

}

