package managedBeans;

/***
 * @author Sai Tarun Bhaskar Gutta
 * G01028963
 * to obtain mean and standard deviation 
 */

public class WiningResult {
private  float mean = 0,standarddeviation = 0;
/**
 * to set mean according to the value
 * @param meanvalue
 */
public void setMean(float meanvalue){
mean = meanvalue;
}
/**
 * 
 * @param standarddeviationvalue
 */
public void setStandarddeviation(float standarddeviationvalue){
this.standarddeviation = standarddeviationvalue;
}
/**
 * 
 * @return mean
 */
public float getMean(){
return mean;
}
/**
 * 
 * @return standarddeviation
 */
public float getStandarddeviation(){
return standarddeviation;
}
}
