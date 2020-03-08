package com.beone.shg.net.webservice.rest.model.gen;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.webservice.rest.support.LinkRESTRelationEnumJsonSerializer;

@JsonSerialize(include=Inclusion.NON_NULL)
public class LinkREST {
	private LinkRESTRelationEnum rel;
	private String type;
	private String href;

	public LinkREST(LinkRESTRelationEnum rel, String type, String href) {
		super();
		this.rel = rel;
		this.type = type;
		this.href = href;
	}

	@JsonSerialize(using=LinkRESTRelationEnumJsonSerializer.class)
	public LinkRESTRelationEnum getRel() {
		return rel;
	}
	public void setRel(LinkRESTRelationEnum rel) {
		this.rel = rel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
}
