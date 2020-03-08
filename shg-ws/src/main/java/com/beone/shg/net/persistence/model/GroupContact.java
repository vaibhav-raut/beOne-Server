package com.beone.shg.net.persistence.model;
// Generated Mar 22, 2014 6:10:19 PM by Hibernate Tools 3.1.0.beta4

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * GroupContact generated by hbm2java
 */
@Entity
@Table(name="group_contact"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class GroupContact  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = -8840475642853261148L;
	private long contactId;
	private GProfile GProfile;
	private Contact contact;
	private Lang lang;
	private String name;
	private String vision;
	private String description;

	// Constructors

	/** default constructor */
	public GroupContact() {
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GroupContactIdGenerator")
	@GenericGenerator(name = "GroupContactIdGenerator", strategy = "com.beone.shg.net.persistence.generator.GroupContactIdGenerator")
	@Column(name="contact_id", unique=true, nullable=false, insertable=true, updatable=true)

	public long getContactId() {
		return this.contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="g_ac_no", unique=false, nullable=false, insertable=true, updatable=true)

	public GProfile getGProfile() {
		return this.GProfile;
	}

	public void setGProfile(GProfile GProfile) {
		this.GProfile = GProfile;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="contact_id", unique=true, nullable=false, insertable=false, updatable=false)

	public Contact getContact() {
		return this.contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	@ManyToOne(cascade={},
			fetch=FetchType.EAGER)

	@JoinColumn(name="lang_id", unique=false, nullable=false, insertable=true, updatable=true)

	public Lang getLang() {
		return this.lang;
	}

	public void setLang(Lang lang) {
		this.lang = lang;
	}
	@Column(name="name", unique=false, nullable=true, insertable=true, updatable=true, length=200)

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="vision", unique=false, nullable=true, insertable=true, updatable=true, length=500)

	public String getVision() {
		return this.vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}
	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=500)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}