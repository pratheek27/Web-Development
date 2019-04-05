package managedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import supportingClasses.StudentService;
import static supportingClasses.StudentService.storage_container_placeholder;;

/***
 * 
 * @Shubham Pudi
 * Managed Bean Student Class
 *
 */

//Managed Bean Created namely "studentBean"
@ManagedBean(name="studentBean", eager=true)
@RequestScoped
public class Student {
	
private String firstname, 
lastname, 
city,
state, 
email, date,
interestedin, 
raffle,
additionalComments,
recommendation,
streetaddress;
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
public void setDate(String date) {
	this.date = date;
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
public String getDate() {
	return date;
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
public void validateDate(FacesContext context, 
		UIComponent componenttoValidate, Object value){
	
	String validateString = (String)value; 
	context = FacesContext.getCurrentInstance();
	FacesMessage message = new FacesMessage("*Date must be in the form MM/DD/YYYY");
	Pattern pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    Matcher matcher = pattern.matcher(validateString);
	String validate_state = null;
    if(!(matcher.matches())){
		throw new ValidatorException(message);
	}else{
			validate_state = validatedatefull(validateString);
			if(validate_state.equals("1")){
			}else if(validate_state.equals("0")){
				throw new ValidatorException(new FacesMessage("*Enter a valid date"));
			}
	}
}
/**
 * 
 * @param context
 * @param componenttobeValidated
 * 
 * validateRaffle to validate the numbers entered into the column and it has 10 commas or not
 */
public void validateRaffle(FacesContext context, 
		UIComponent componenttobeValidated,Object value){
	
	String validateString = (String)value; 
	context = FacesContext.getCurrentInstance();
	FacesMessage message = new FacesMessage("*Invalid raffle ticket number, Enter 10 comma separated values between 1 and 100");

	if(!(validateRaffleno(validateString)))
		throw new ValidatorException(message);
	
}
/*
 * Returns the acknowledgement page based on the correct details entered and returns error based on wrongful information on the survey
 */
public String submit(){
	
	
	winner.setMean(student.calculateMean(this.getRaffle()));
	winner.setStandarddeviation(student.calculateStandardDeviation(this.getRaffle()));
	storage_container_placeholder.add(this);
	if(winner.getMean() > 90)
		return "WinnerAcknowledgement";
	else
		return "SimpleAcknowledgement";
}
/**
 * 
 * returns details of the students entered in the students survey form
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
 * validates date completely
 * @param string
 * @return result
 */
public String validatedatefull(String string){
	String[] intarray = string.split("/");
	String result = null;
	int count = 0;
	
	if(Integer.parseInt(intarray[count++]) > 12 || Integer.parseInt(intarray[count++]) > 31 || Integer.parseInt(intarray[count]) > 9999)	
		result = "0";
	else
		result = "1";
	
	
	return result;
}

}
