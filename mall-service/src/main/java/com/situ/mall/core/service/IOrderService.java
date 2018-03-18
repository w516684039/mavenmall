package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.Order;

public interface IOrderService {
	void add(Order order);
	Order selectOrderByUserId(Integer id);
	List<Order> selectByUserId(Integer id);
	ServerResponse<List<Order>> selectAlls(Integer page, Integer limit, Order order);
}
