package com.beone.shg.net.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="message_type"
,catalog="shg"
, uniqueConstraints = {  }
		)
public class MessageType  implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 1859038640347493416L;
	private int messageTypeId;
	private Lang lang;
	private String messageType;
	private String smsFormat;
	private String emailFormat;
	private long passKey;

	// Constructors

	/** default constructor */
	public MessageType() {
	}

	public MessageType(Lang lang, String messageType, String smsFormat,
			String emailFormat, long passKey) {
		super();
		this.lang = lang;
		this.messageType = messageType;
		this.smsFormat = smsFormat;
		this.emailFormat = emailFormat;
		this.passKey = passKey;
	}

	public MessageType(int messageTypeId, Lang lang, String messageType, String smsFormat,
			String emailFormat, long passKey) {
		super();
		this.messageTypeId = messageTypeId;
		this.lang = lang;
		this.messageType = messageType;
		this.smsFormat = smsFormat;
		this.emailFormat = emailFormat;
		this.passKey = passKey;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="message_type_id", unique=true, nullable=false, insertable=true, updatable=true)
	public int getMessageTypeId() {
		return messageTypeId;
	}

	public void setMessageTypeId(int messageTypeId) {
		this.messageTypeId = messageTypeId;
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

	@Column(name="message_type", unique=false, nullable=false, insertable=true, updatable=true, length=30)
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Column(name="sms_format", unique=false, nullable=true, insertable=true, updatable=true, length=500)
	public String getSmsFormat() {
		return smsFormat;
	}

	public void setSmsFormat(String smsFormat) {
		this.smsFormat = smsFormat;
	}

	@Column(name="email_format", unique=false, nullable=true, insertable=true, updatable=true, length=1000)
	public String getEmailFormat() {
		return emailFormat;
	}

	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}

	@Column(name="pass_key", unique=false, nullable=true, insertable=true, updatable=true)
	public long getPassKey() {
		return passKey;
	}

	public void setPassKey(long passKey) {
		this.passKey = passKey;
	}
}
