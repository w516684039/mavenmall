package com.situ.mall.portal.vo;

import com.situ.mall.core.entity.Product;

public class CartItemVo {

	private Product product;
	private Integer amount;
	private Integer isChecked;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}
	@Override
	public String toString() {
		return "CartItemVo [product=" + product + ", amount=" + amount + ", isChecked=" + isChecked + "]";
	}
	

}
