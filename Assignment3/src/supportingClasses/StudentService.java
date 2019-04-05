/**
 * @author Shubham Pudi
 * 
 */
package supportingClasses;
import java.util.ArrayList;

import managedBeans.Student;
/**
 * For storing survey details in a placeholder container and calculation mean and Standard Deviation
 */
public class StudentService {
//Static Collection Placeholder to store the student survey form details  
public static ArrayList<Student> storage_container_placeholder = new ArrayList<Student>();
public static void add(Student student){
storage_container_placeholder.add(student);
}
	/**
	 * returns the mean of the numbers in the raffle ticket
	 * @param rafflestring
	 * @return mean/10
	 */
	public float calculateMean(String rafflestring){
		float mean = 0;
		String[] integerarray = rafflestring.split(",");
		int i = 0;
				do{
					mean = mean + Integer.parseInt(integerarray[i].trim());
					i++;
			}while(i<integerarray.length);
		return (mean/10);
	}
	/**
	 * calculate standard deviation and return the standarddeviation
	 * @param rafflestring
	 * @return standarddeviation
	 */
	public float calculateStandardDeviation(String rafflestring){
		float standarddeviation = 0;
		String[] integerarray = rafflestring.split(",");
		float mean = calculateMean(rafflestring);
		float[] differencearray = new float[10];
		int i=0;
		while(i<integerarray.length)
		{
			differencearray[i] = mean - Integer.parseInt(integerarray[i].trim());
			i++;
		}
		for(i=0; i<differencearray.length; i++){
			 differencearray[i]*=differencearray[i];
			 standarddeviation += differencearray[i];
		}
		return standarddeviation/10;
	}
}
