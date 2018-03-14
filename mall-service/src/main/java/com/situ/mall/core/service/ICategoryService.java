package com.situ.mall.core.service;

import java.util.List;

import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.Category;

public interface ICategoryService {
	
	ServerResponse selectTopCategory();
	ServerResponse selectSecondCategory(Integer topCategoryId);
	Integer selectParentCategoryId(Integer categoryId);
	ServerResponse<List<Category>> pageList(Integer page, Integer limit, Category name);
	ServerResponse deleteById(Integer id);
	ServerResponse add(Category category);
	ServerResponse deleteAll(String ids);
	Category selectById(Integer id);
	List<Category> selectCategory();
	List<Category> selectTopCategoryList();
	List<Category> selectSecondCategoryList();
}
