package com.situ.mall.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.constant.Const;
import com.situ.mall.core.entity.OrderItem;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.entity.User;
import com.situ.mall.core.service.IOderItemService;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOderItemService oderItemService;

	@RequestMapping("/pageList")
	@ResponseBody
	public ServerResponse<List<OrderItem>> pageList(Integer page, Integer limit, OrderItem orderItem) {
		return oderItemService.pageList(page, limit, orderItem);
	}
	@RequestMapping("/getOderPage")
	public String getOderPage(){
		return "order";
	}

}
