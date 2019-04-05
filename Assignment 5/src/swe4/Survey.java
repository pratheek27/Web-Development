package swe4;



import java.io.Serializable;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
* 
*/
@ManagedBean
public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;
	public String choice = "VeryLikely,Likely,UnLikely";
	public String[] choicearray = choice.split(",");

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	Student student = new Student();

	/**
	 * @return student(Student)
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param stud
	 */
	public void setStudent(Student stud) {
		this.student = stud;
	}

	Searchbean search = new Searchbean();

	/**
	 * @return search(Sbean)
	 */
	public Searchbean getS() {
		return search;
	}

	/**
	 * @param s
	 */
	public void setS(Searchbean s) {
		this.search = s;
	}

	StudentService s1 = new StudentService();

	/**
	 * @return
	 */
	public StudentService getS1() {
		return s1;
	}

	/**
	 * @param s1
	 */
	public void setS1(StudentService s1) {
		this.s1 = s1;
	}

	WinningResult wr = new WinningResult();

	/**
	 * @return
	 */
	public WinningResult getWr() {
		return wr;
	}

	/**
	 * @param wr
	 */
	public void setWr(WinningResult wr) {
		this.wr = wr;
	}

	/**
	 * @param choices
	 * @return
	 */
	public List<String> display(String choices) {
		List<String> abc = new ArrayList<String>();
		for (String i : choicearray) {
			if (i.toUpperCase().startsWith(choices.toUpperCase())) {
				abc.add(i);
			}
		}
		return (abc);
	}

	String ack[] = { "Acknowledgement" };

	/**
	 * @return
	 */
	public String[] getack() {
		return ack;
	}

	/**
	 * @param ack
	 */
	public void setack(String[] ack) {
		this.ack = ack;
	}

	ArrayList<Student> studentsarray = new ArrayList<Student>();

	/**
	 * @return
	 */
	public ArrayList<Student> getStudentarray() {
		return studentsarray;
	}

	/**
	 * @param sarray
	 */
	public void setStudentarray(ArrayList<Student> studentarray) {
		this.studentsarray = studentarray;
	}

	/**
	 * @return
	 */
	public String submitAction() {

		StudentService ss = new StudentService();
		double mean = ss.calculateMean(student);
		double deviation = ss.calculateSDeviation(student);
		wr.setMean(mean);
		student.setMean(mean);
		wr.setDeviation(deviation);
		student.setDeviation(deviation);
		student.setDeviation(wr.getDeviation());
		student.setMean(wr.getMean());
		try {
			SaveData.studentsData(student);
		} catch (Exception e) {
			e.printStackTrace();

		}

		if (wr.getMean() > 90) {
			return "winningResult";
		} else {
			return "SimpleAcknowledgement";
		}
	}
	
	public void renderAll()
	{
		
		final String REST_URI = "http://54.165.4.240/As3/webresources/zips/";
		String result = null; 
		student.setState(null); 
		student.setCity(null); 
		String[] result_array = null;
		try{
			Client client = ClientBuilder.newClient(); 
			WebTarget webTarget = client.target(REST_URI).path("{zip}").resolveTemplate("zip",student.getZip()); 
			Invocation.Builder invocationBuilder = webTarget.request(); 
			Response response = invocationBuilder.get(); 
			result = response.readEntity(String.class); 
			result_array = result.split(",");
			//System.out.println("Thingin array"+result_array[0]);
			if(result_array.length == 2){
				
			student.setCity(result_array[0]); 
			student.setState(result_array[1]);
			}
		  }catch(Exception e){
			e.printStackTrace(); 
		  }
		
	}

 
	public ArrayList<Student> getStudentsarray() {

		EntityManager em = SaveData.getEntityManager();

		return (ArrayList<Student>) em.createNamedQuery("selectall", Student.class).getResultList();
	}

	

	/**
	 * @param context
	 * @param componentToValidate
	 * @param value
	 * @throws ValidatorException
	 */
	public void dateAfter(FacesContext context, UIComponent componentToValidate, Object value)
			throws ValidatorException {
		Date SemStartDate = ((Date) value);

		Object surveyDateValue = componentToValidate.getAttributes().get("dateOfSurvey");
		Date SurveyDate = (Date) ((org.primefaces.component.calendar.Calendar) surveyDateValue).getValue();

		System.out.println(" semDate= " + SemStartDate);
		if (SemStartDate == null)
			return;

		if (SemStartDate.before(SurveyDate)) {
			FacesMessage message = new FacesMessage("Semester Start date cannot be before Survey date.");
			throw new ValidatorException(message);
		}

	}

}
