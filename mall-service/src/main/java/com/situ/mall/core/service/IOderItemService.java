package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.OrderItem;

public interface IOderItemService {
	void addOrderItem(OrderItem orderItem);
	
	List<OrderItem> selectByOrderNo(Long orderNo);
	ServerResponse<List<OrderItem>> pageList(Integer page,Integer limit,OrderItem orderItem);
}
