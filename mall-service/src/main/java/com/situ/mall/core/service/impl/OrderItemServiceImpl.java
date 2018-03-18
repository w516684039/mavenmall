package com.situ.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.mapper.OrderItemMapper;
import com.situ.mall.core.service.IOderItemService;
@Service
public class OrderItemServiceImpl implements IOderItemService {

	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Override
	public void addOrderItem(OrderItem orderItem) {
		orderItemMapper.insertSelective(orderItem);
	}

	@Override
	public List<OrderItem> selectByOrderNo(Long orderNo) {
		return orderItemMapper.selectByOrderNo(orderNo);
	}

	@Override
	public ServerResponse<List<OrderItem>> pageList(Integer page, Integer limit, OrderItem orderItem) {
		PageHelper.startPage(page,limit);
		List<OrderItem> list = orderItemMapper.pageList(orderItem);
		PageInfo pageInfo = new PageInfo(list);
		Integer count = (int)pageInfo.getTotal();
		return ServerResponse.createSuccess("查询成功",count,list);
	}

	

}
