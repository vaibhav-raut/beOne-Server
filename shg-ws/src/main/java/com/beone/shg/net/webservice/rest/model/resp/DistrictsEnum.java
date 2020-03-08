package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DistrictsEnum {

	private String lang;
	private List<DistrictValue> districtValues;
	
	public DistrictsEnum() {
		super();
	}
	public DistrictsEnum(String lang, List<DistrictValue> districtValues) {
		super();
		this.lang = lang;
		this.districtValues = districtValues;
	}
	public DistrictsEnum(String lang, CSVDataCollector csvData) {
		super();
		this.lang = lang;
		this.districtValues = new ArrayList<DistrictValue>(csvData.getNoOfRows());
		
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			districtValues.add(buildDistrictValue(lang, csvData, row));		
		}
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public List<DistrictValue> getDistrictValues() {
		return districtValues;
	}
	public void setDistrictValues(List<DistrictValue> districtValues) {
		this.districtValues = districtValues;
	}
	public void addDistrictValue(DistrictValue districtValue) {

		if(districtValues == null) {
			districtValues = new ArrayList<DistrictValue>();
		}
		this.districtValues.add(districtValue);
	}	
	
	public static DistrictValue buildDistrictValue(String lang, CSVDataCollector csvData, int row) {
		
		DistrictValue value = new DistrictValue();
		
		value.setLang(lang);
		value.setState(csvData.getCellValue("State", row));
		value.setDivision(csvData.getCellValue("Division", row));
		value.setDistrict(csvData.getCellValue("District", row));
		value.setDistrictCode(csvData.getCellValue("District Code", row));

		return value;
	}
	
	public static boolean isDistrictCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("State") &&
				csvData.isColumnPresent("Division") &&
				csvData.isColumnPresent("District") &&
				csvData.isColumnPresent("District Code"));
	}
}
