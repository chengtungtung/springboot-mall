package com.hahow.tung.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class CreateOrderRequest {

	@NotEmpty // 此註解用在list或map類型的變數上，用來驗證此集合不能為空
	private List<BuyItem> buyItemList;

	public List<BuyItem> getBuyItemList() {
		return buyItemList;
	}

	public void setBuyItemList(List<BuyItem> buyItemList) {
		this.buyItemList = buyItemList;
	}
	
	
	
}
