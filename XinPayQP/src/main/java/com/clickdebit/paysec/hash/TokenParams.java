package com.clickdebit.paysec.hash;

import java.util.Date;

public class TokenParams {
	private String transactionId;
	private String transactionUUID;
	private String issuer;
	private Date issuedAt;
	private Date expirationDate;
	private String audience;
	private String version;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionUUID() {
		return transactionUUID;
	}
	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public Date getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "TokenParams [transactionId=" + transactionId + ", transactionUUID=" + transactionUUID + ", issuer="
				+ issuer + ", issuedAt=" + issuedAt + ", expirationDate=" + expirationDate + ", audience=" + audience
				+ ", version=" + version + "]";
	}
	
	
}
