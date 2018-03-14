package com.situ.mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.mall.core.entity.Product;
import com.situ.mall.core.service.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IProductService productService;
	
	@RequestMapping("/getProductListPage")
	public String getProductListPage(Integer categoryId,Model model){
		List<Product> list = productService.selectCategoryId(categoryId);
		model.addAttribute("list",list);
		return "product_list";
	}
	
	@RequestMapping("/getProductDetail")
	public String getProductDetail(Integer productId,Model model){
		Product product = productService.selectById(productId);
		model.addAttribute("product", product);
		return "product_detail";
	}
}
