package com.situ.mall.core.mapper;

import java.util.List;

import com.situ.mall.core.entity.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    List<OrderItem> selectByOrderNo(Long orderNo);
    
    List<OrderItem> pageList(OrderItem orderItem);
}