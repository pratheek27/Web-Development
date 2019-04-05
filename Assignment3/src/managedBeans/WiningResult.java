package managedBeans;

/***
 * Shubham Pudi
 * To calculate the Mean and Standard Deviation of the numbers entered through raffle
 */

public class WiningResult {
private  float mean = 0,standarddeviation = 0;
/**
 * To set mean according to the value
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
