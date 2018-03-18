package com.situ.mall.core.mapper;

import java.util.List;

import com.situ.mall.core.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    Order selectByUserId(Integer id);
    
    List<Order> selectByUserrId(Integer userid);
    
    List<Order> selectAlls();
}