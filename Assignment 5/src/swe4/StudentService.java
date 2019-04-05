package swe4;



import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

/**
* 
*/
public class StudentService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param student
	 * @return
	 */
	public double calculateMean(Student student) {
		String r[] = (student.getRaffle()).split(",");

		int sum = 0;

		int i;
		for (i = 0; i < r.length; i++) {
			int c = Integer.parseInt(r[i]);
			sum += c;
		}

		return sum / i;

	}

	public double calculateSDeviation(Student student) {
		String r[] = (student.getRaffle()).split(",");
		int raffleTicket[] = new int[20];
		int i = 0;
		int sum = 0;
		double deviation;
		for (String string : r) {
			raffleTicket[i] = Integer.parseInt(string);
			i++;
		}

		double mean = calculateMean(student);
		for (int j = 0; j < r.length; j++) {
			sum += Math.pow((raffleTicket[j] - mean), 2);

		}
		deviation = Math.pow(sum / (r.length - 1), 1 / 2);

		return deviation;
	}

}