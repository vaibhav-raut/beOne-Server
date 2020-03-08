package com.beone.shg.net.webservice.rest.model.gen;

//TODO: refactor to ionroad response

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author wacosta
 *
 */
@JsonSerialize(include=Inclusion.NON_NULL)
public class SpeedDataTileREST {
	
	
	/**
	 * A list of data points contained in this tile
	 */
	private List<SpeedDataPointREST> dataPoints;
	
	
	/**
	 * the REST url links
	 */
	private List<LinkREST> links;
	
	
	private String id;
	private long dataVersion;
	private double centerLatitude;
	private double centerLongitude;
	
	/**
	 * the size of the tile expressed as degrees.  This API assumes tiles are "square"
	 * meaning that specifying a tile of N degrees, the API will return a tile of size
	 * N degrees latitude and N degrees longitude.   
	 */
	private double size;
	
	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public List<LinkREST> getLinks() {
		return links;
	}

	public long getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(long dataVersion) {
		this.dataVersion = dataVersion;
	}

	public double getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public double getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public void setLinks(List<LinkREST> links) {
		this.links = links;
	}

	public List<SpeedDataPointREST> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(List<SpeedDataPointREST> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
