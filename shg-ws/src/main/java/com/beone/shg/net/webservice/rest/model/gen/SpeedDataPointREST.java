package com.beone.shg.net.webservice.rest.model.gen;

// TODO: refactor to ionroad response

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class SpeedDataPointREST {

	private double latitude;
	private double longitude;
	private float bearing;
	private Map<Integer, Double> speedHistogram;
	private double medianSpeed;
	private double meanSpeed;
	private double commonSpeed;
	private double meanVariance;
	private double medianVariance;
	private int numDataPoints;
	private int numUsers;

	public int getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}

	public int getNumDataPoints() {
		return numDataPoints;
	}

	public void setNumDataPoints(int numDataPoints) {
		this.numDataPoints = numDataPoints;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getBearing() {
		return bearing;
	}

	public void setBearing(float bearing) {
		this.bearing = bearing;
	}

	public Map<Integer, Double> getSpeedHistogram() {
		return speedHistogram;
	}

	public void setSpeedHistogram(Map<Integer, Double> speedHistogram) {
		this.speedHistogram = speedHistogram;
	}

	public double getMedianSpeed() {
		return medianSpeed;
	}

	public void setMedianSpeed(double medianSpeed) {
		this.medianSpeed = medianSpeed;
	}

	public double getMeanSpeed() {
		return meanSpeed;
	}

	public void setMeanSpeed(double meanSpeed) {
		this.meanSpeed = meanSpeed;
	}

	public double getCommonSpeed() {
		return commonSpeed;
	}

	public void setCommonSpeed(double commonSpeed) {
		this.commonSpeed = commonSpeed;
	}

	public double getMeanVariance() {
		return meanVariance;
	}

	public void setMeanVariance(double meanVariance) {
		this.meanVariance = meanVariance;
	}

	public double getMedianVariance() {
		return medianVariance;
	}

	public void setMedianVariance(double medianVariance) {
		this.medianVariance = medianVariance;
	}
}