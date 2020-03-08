package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MRole generated by hbm2java
 */
@Entity
@Table(name="doc_type"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class DocType  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;
	private int docTypeId;
	private String docType;
	private String docFor;
	private String docCategory;
	private String docDesc;

	// Constructors

	/** default constructor */
	public DocType() {
	}

	public DocType(String docType, String docFor, String docCategory,
			String docDesc) {
		super();
		this.docType = docType;
		this.docFor = docFor;
		this.docCategory = docCategory;
		this.docDesc = docDesc;
	}

	public DocType(int docTypeId, String docType, String docFor,
			String docCategory, String docDesc) {
		super();
		this.docTypeId = docTypeId;
		this.docType = docType;
		this.docFor = docFor;
		this.docCategory = docCategory;
		this.docDesc = docDesc;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="doc_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}
	@Column(name="doc_type", unique=false, nullable=false, insertable=true, updatable=true, length=50)

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}
	@Column(name="doc_for", unique=false, nullable=false, insertable=true, updatable=true, length=100)

	public String getDocFor() {
		return docFor;
	}

	public void setDocFor(String docFor) {
		this.docFor = docFor;
	}
	@Column(name="doc_category", unique=false, nullable=false, insertable=true, updatable=true, length=100)

	public String getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}
	@Column(name="doc_desc", unique=false, nullable=false, insertable=true, updatable=true, length=100)

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
}