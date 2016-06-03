package com.nzion.zkoss.composer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.nzion.domain.Patient;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * May 2, 2011
 */
public class AgeBasedLMSZGrowthChartController extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	
	private final ChartController chartController;
	
	public AgeBasedLMSZGrowthChartController(String csvDataFile, String xAxisUnit, String yAxisUnit, String gender,String chartTitle,Patient patient) throws IOException {
	chartController = new ChartController(chartTitle,xAxisUnit,yAxisUnit);
	InputStream stream = getClass().getClassLoader().getResourceAsStream(csvDataFile); 
	BufferedReader dataFileReader = new BufferedReader(new InputStreamReader(stream));
	String justRead = dataFileReader.readLine();
	String[] headers = justRead.split(",");
	int age = UtilDateTime.getIntervalInYears(patient.getDateOfBirth(), patient.getDateOfDeath()==null?new Date():patient.getDateOfDeath());
	while(true){
		justRead = dataFileReader.readLine();
		if(justRead == null)
			break;
		String[] data = justRead.split(",");
		if(!data[0].trim().equals(gender))
			continue;
		for(int i = 5; i < headers.length; ++i){
			if(Double.valueOf(data[1])< (age+1)*12)
			chartController.addValue(headers[i], (Number)Double.valueOf(data[1]), findY(data[2], data[3], data[4], data[i]));
		}
	}
	}
	
	public void addPatientRecord(String category, String months, String value){
	if(UtilValidator.isEmpty(category) || UtilValidator.isEmpty(months) || UtilValidator.isEmpty(value))
		return;
	chartController.addValue(category, (Number)Double.valueOf(months), Double.parseDouble(value));
	}
	
	public Double findY(String lString, String mString, String sString, String xString){
	double l = Double.parseDouble(lString);
	double m = Double.parseDouble(mString);
	double s = Double.parseDouble(sString);
	double x = Double.parseDouble(xString);
	double r = m * Math.pow(( 1 + ( l * s * (( Math.pow(x/m, l) - 1)/(l * s)))), (1 / l));
	return r;
	}

	public ChartController getChartController() {
	return chartController;
	}
}