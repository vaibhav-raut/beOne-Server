package com.beone.shg.net.webservice.rest.model.resp;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.RESTConst;

@JsonSerialize(include = Inclusion.NON_NULL)
public class FileLink {

	private String success;
	private String link;
	public FileLink() {
		this.success = RESTConst.FAILURE[0];
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
