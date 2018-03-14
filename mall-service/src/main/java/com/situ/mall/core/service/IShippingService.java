package com.situ.mall.core.service;

import com.situ.mall.core.entity.Shipping;

public interface IShippingService {

	Shipping findByUserId(Integer id);
}
