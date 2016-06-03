package com.nzion.domain;

import java.io.Serializable;

import javax.persistence.Transient;

import com.nzion.util.UtilValidator;

/**
 * @author Nafis
 *
 */
public class PostalAddressFields implements Serializable{

	private static final long serialVersionUID = 1L;

	private String toName;

	private String attnName;

	private String address1;

	private String address2;

	private String directions;

	private String city;

	private String postalCode;
	
	private String zipCode;

	private String postalCodeExt;

	private String countryGeo;

	private String stateProvinceGeo;

	private String countyGeo;

	private String geoPoint;
	
	public String getAddress1() {
	return address1;
	}

	public String getAddress2() {
	return address2;
	}

	public String getAttnName() {
	return attnName;
	}

	public String getCity() {
	return city;
	}

	public String getCountryGeo() {
	return countryGeo;
	}

	public String getCountyGeo() {
	return countyGeo;
	}

	public String getDirections() {
	return directions;
	}

	public String getGeoPoint() {
	return geoPoint;
	}

	public String getPostalCode() {
	return postalCode;
	}

	public String getPostalCodeExt() {
	return postalCodeExt;
	}

	public String getStateProvinceGeo() {
	return stateProvinceGeo;
	}

	public String getToName() {
	return toName;
	}

	public void setAddress1(String address1) {
	this.address1 = address1;
	}

	public void setAddress2(String address2) {
	this.address2 = address2;
	}

	public void setAttnName(String attnName) {
	this.attnName = attnName;
	}

	public void setCity(String city) {
	this.city = city;
	}

	public void setCountryGeo(String countryGeo) {
	this.countryGeo = countryGeo;
	}

	public void setCountyGeo(String countyGeo) {
	this.countyGeo = countyGeo;
	}

	public void setDirections(String directions) {
	this.directions = directions;
	}

	public void setGeoPoint(String geoPoint) {
	this.geoPoint = geoPoint;
	}

	public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
	if(this.postalCode!=null){
		if(postalCode.length()>=5){
		zipCode=postalCode.substring(0, 5);
		}
		else
			zipCode = postalCode;
		if(postalCode.length()>6)
		postalCodeExt = postalCode.substring(6);
	}
	}
	
	@Transient
	public String getZipCode() {
	return zipCode;
	}

	public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
	}

	public void setPostalCodeExt(String postalCodeExt) {
	this.postalCodeExt = postalCodeExt;
	}

	public void setStateProvinceGeo(String stateProvinceGeo) {
	this.stateProvinceGeo = stateProvinceGeo;
	}

	public void setToName(String toName) {
	this.toName = toName;
	}
	
	@Override
	public String toString() {
	StringBuilder builder  = new StringBuilder();
	builder.append(UtilValidator.isEmpty(address1)  ? "" : address1 +", ");
	builder.append(UtilValidator.isEmpty(address2) ? "" : address2 + ", ");
	builder.append(UtilValidator.isEmpty(city) ? "" : city + ", ");
	builder.append(UtilValidator.isEmpty(stateProvinceGeo) ? "" : stateProvinceGeo + " ");
	builder.append(UtilValidator.isEmpty(postalCode) ? "" : postalCode );
	if(builder.length()>0 && builder.charAt(builder.length()-1)==',')
		builder.deleteCharAt(builder.length()-1);
	return builder.toString();
	}
}