package com.clickdebit.paysec.connector.response.model;

public class XinPayQPQueryResponse {
    
	private String cartId;
	private Double amount;
	private String merchantId;
	private String signature;
	private String signType;
	private String status;
	private String error;
	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "XinPayQPQueryResponse [cartId=" + cartId + ", amount=" + amount + ", merchantId=" + merchantId
				+ ", signature=" + signature + ", signType=" + signType + ", status=" + status + ", error=" + error
				+ "]";
	}
	
	
}
