package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MRole generated by hbm2java
 */
@Entity
@Table(name="doc"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class Doc  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;
	private long docId;
	private DocType docType;
	private int groupAcNo;
	private byte[] file;
	private Date updateTs;

	// Constructors

	/** default constructor */
	public Doc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="doc_id", unique=true, nullable=false, insertable=true, updatable=true)

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="doc_type_id", unique=false, nullable=false, insertable=true, updatable=true)

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	
	@Column(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)
	public int getGroupAcNo() {
		return groupAcNo;
	}

	public void setGroupAcNo(int groupAcNo) {
		this.groupAcNo = groupAcNo;
	}

	@Lob
	@Column(name = "file", nullable=false, columnDefinition="mediumblob")
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Column(name="update_ts", unique=false, nullable=true, insertable=true, updatable=true, length=19)

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}
}
