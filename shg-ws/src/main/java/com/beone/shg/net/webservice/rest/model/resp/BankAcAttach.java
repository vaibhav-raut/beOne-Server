package com.beone.shg.net.webservice.rest.model.resp;

import java.math.BigDecimal;

public class BankAcAttach {
	private long uploadId;
	private long bankAccountNo;
	private String fileName;
	private BigDecimal amount;
	private String uploadDate;
	public BankAcAttach(long uploadId, long bankAccountNo, String fileName,
			BigDecimal amount, String uploadDate) {
		super();
		this.uploadId = uploadId;
		this.bankAccountNo = bankAccountNo;
		this.fileName = fileName;
		this.amount = amount;
		this.uploadDate = uploadDate;
	}
	public long getUploadId() {
		return uploadId;
	}
	public void setUploadId(long uploadId) {
		this.uploadId = uploadId;
	}
	public long getBankAccountNo() {
		return bankAccountNo;
	}
	public void setBankAccountNo(long bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
}
