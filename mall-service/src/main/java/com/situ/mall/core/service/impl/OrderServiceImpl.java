package com.situ.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.Order;
import com.situ.mall.core.mapper.OrderMapper;
import com.situ.mall.core.service.IOrderService;
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Override
	public void add(Order order) {
		orderMapper.insertSelective(order);
	}
	@Override
	public Order selectOrderByUserId(Integer id) {
		return orderMapper.selectByUserId(id);
	}
	@Override
	public List<Order> selectByUserId(Integer id) {
		return orderMapper.selectByUserrId(id);
	}
	@Override
	public ServerResponse<List<Order>> selectAlls(Integer page, Integer limit, Order order) {
		PageHelper.startPage(page,limit);
		List<Order> list = orderMapper.selectAlls();
		Integer count = (int) ((Page)list).getTotal();
		return ServerResponse.createSuccess("查询成功", count, list);
	}

}
