package com.situ.mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.situ.mall.core.entity.Category;
import com.situ.mall.core.service.ICategoryService;

@Controller
public class IndexController {

	@Autowired
	private ICategoryService categoryService;
	
	@RequestMapping("/index")
	public String index(Model model){
		//1级分类
		List<Category> topCategoryList = categoryService.selectTopCategoryList();
		model.addAttribute("topCategoryList", topCategoryList);
		
		//2级分类
		List<Category> secondCategoryList = categoryService.selectSecondCategoryList();
		model.addAttribute("secondCategoryList", secondCategoryList);
		return "index";
	}
}
