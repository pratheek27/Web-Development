package managedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import supportingClasses.StudentService;
import static supportingClasses.StudentService.storage_container_placeholder;;

/***
 * 
 * @author Shubham Pudi
 * G01087664
 *
 */
@ManagedBean(name="studentBean", eager=true)
@RequestScoped
public class Student {
	public String interestOptions="VeryLikely,Likely,UnLikely";
	public String[] optionArray=interestOptions.split(","); 
private String firstname, 
lastname, 
city,
state, 
email, 
interestedin, 
raffle,
additionalComments,
recommendation,
streetaddress;
private Date surveyDate,startDate;
private String phonenumber,zipcode; 

public String getStreetaddress() {
	return streetaddress;
}
public void setStreetaddress(String streetaddress) {
	this.streetaddress = streetaddress;
}
private List<String> campusOptions;
private StudentService student;
private WiningResult winner;
public Student(){
	student = new StudentService();
	winner = new WiningResult();
	
}
/*
 * getters and setters method
 */
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public void setCity(String city) {
	this.city = city;
}
public void setState(String state) {
	this.state = state;
}
public void setEmail(String email) {
	this.email = email;
}
public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
}
public void setSurveyDate(Date surveydate) {
	this.surveyDate = surveydate;
}
public void setStartDate(Date startdate) {
	this.startDate = startdate;
}
public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
	

	
}
public void setCampusOptions(List<String> campusOptions) {
	this.campusOptions = campusOptions;
}
public void setInterestedin(String interestedin) {
	this.interestedin = interestedin;
}
public void setRecommendation(String recommendation) {
	
	this.recommendation = recommendation;
	System.out.println(recommendation);
}
public void setRaffle(String raffle) {
	this.raffle = raffle;
}
public void setAdditionalComments(String additionalComments) {
	this.additionalComments = additionalComments;
}
public String getFirstname() {
	return firstname;
}
public String getLastname() {
	return lastname;
}
public String getCity() {
	return city;
}
public String getState() {
	return state;
}
public String getEmail() {
	return email;
}
public String getPhonenumber() {
	return phonenumber;
}
public String getZipcode() {
	return zipcode;
}
public Date getSurveyDate() {
	return surveyDate;
}
public Date getStartDate() {
	return startDate;
}
public List<String> getCampusOptions() {
	return campusOptions;
}
public String getInterestedin() {
	return interestedin;
}
public String getRecommendation() {
	return recommendation;
}
public String getRaffle() {
	return raffle;
}
public String getAdditionalComments() {
	return additionalComments;
}
public float getMean(){
	return winner.getMean();
}
public float getStandarddeviation(){
	return winner.getStandarddeviation();
}
/**
 * check whether entered date is valid or not
 * @param context
 * @param componenttoValidate
 * @param value
 */
public void validateStartDate(FacesContext context, 
		UIComponent componenttoValidate, Object value){
	
	
	Date validatedate=(Date)value;
context =FacesContext.getCurrentInstance();
FacesMessage message = new FacesMessage("Semester Start Date must not be less than survey date");
Object surveyObj = componenttoValidate.getAttributes().get("surveyDate");
Date surveyDate1 = (Date)((org.primefaces.component.calendar.Calendar)surveyObj).getValue();;

if (validatedate.before(surveyDate1))
{
	throw new ValidatorException(message);
}

}
public void validateRaffle(FacesContext context, 
		UIComponent componenttobeValidated,Object value){
	
	String validateString = (String)value; 
	context = FacesContext.getCurrentInstance();
	FacesMessage message = new FacesMessage("*Invalid raffle ticket number, Enter 10 comma separated values between 1 and 100");

	if(!(validateRaffleno(validateString)))
		throw new ValidatorException(message);
	
}

public String submit(){
	
	
	winner.setMean(student.calculateMean(this.getRaffle()));
	winner.setStandarddeviation(student.calculateStandardDeviation(this.getRaffle()));
	System.out.println("The city is"+getCity());
	System.out.println("The state is"+getState());
	//storing the class object in student service class
	storage_container_placeholder.add(this);
	if(winner.getMean() > 90)
		return "WinnerAcknowledgement";
	else
		return "SimpleAcknowledgement";
}
/**
 * 
 * @return details of the students entered in the student survey form
 */
public ArrayList<Student> getStudents(){
	return storage_container_placeholder;
}
/**
 * Helps us validate whether the raffle ticket number entered is valid or not
 * @param validateString
 * @return true or false
 */
public boolean validateRaffleno(String validateString){
	
	String[] integerArray = validateString.split(",");

	for(int i=0 ;i<integerArray.length; i++){
		if(integerArray[i] == "")
			continue;
		else
			integerArray[i] = integerArray[i].trim();
	}
	
	try{
		if(integerArray.length > 10){
				return false;
			}else{
				for(int i=0; i<integerArray.length; i++){
				   if(Integer.parseInt(integerArray[i]) > 100){
					   return false;
				   }
				}
				return true;
			}
	}catch(NumberFormatException e){
		return false;
	}
}


/**
 * auto completes interest options
 * @param string
 * @return List<String> 
 */
public List<String> display(String prefix) {
	List<String> matches = new ArrayList<String>(); 
	for(String possibleOption: optionArray) {
	if(possibleOption.toUpperCase() .startsWith(prefix.toUpperCase())) {
	        matches.add(possibleOption);
	      }
	}
	    return(matches);
	  }
/**
 * Listener method for Ajax
 */
public void renderAll()
{
	
	final String REST_URI = "http://54.167.81.140/As3/webresources/zips/";
	String result = null; 
	state = null; 
	city = null; 
	String[] result_array = null;
	try{
		Client client = ClientBuilder.newClient(); 
		WebTarget webTarget = client.target(REST_URI).path("{zip}").resolveTemplate("zip",getZipcode()); 
		Invocation.Builder invocationBuilder = webTarget.request(); 
		Response response = invocationBuilder.get(); 
		result = response.readEntity(String.class); 
		result_array = result.split(",");
		System.out.println("Thingin array"+result_array[0]);
		if(result_array.length == 2){
			
		city = result_array[0]; 
		state = result_array[1];
		}
	  }catch(Exception e){
		e.printStackTrace(); 
	  }
	
}


}
