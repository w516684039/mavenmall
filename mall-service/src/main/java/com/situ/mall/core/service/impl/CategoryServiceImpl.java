package com.situ.mall.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.situ.mall.common.response.ServerResponse;
import com.situ.mall.core.entity.Category;
import com.situ.mall.core.entity.Product;
import com.situ.mall.core.mapper.CategoryMapper;
import com.situ.mall.core.service.ICategoryService;
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
   
	
	@Override
	public ServerResponse selectTopCategory() {
		List<Category> list = categoryMapper.selectTopCategory();
		if (list == null || list.size() == 0) {
			return ServerResponse.createError("没有一级分类");
		}
		return ServerResponse.createSuccess("查找一级分类成功",list);
	}

	@Override
	public ServerResponse selectSecondCategory(Integer topCategoryId) {
		List<Category> list = categoryMapper.selectSecondCategory(topCategoryId);
		if (list == null || list.size() == 0 ) {
			return ServerResponse.createError("没有二级分类");
		}
		return ServerResponse.createSuccess("查找二级分类成功",list);
	}

	@Override
	public Integer selectParentCategoryId(Integer categoryId) {
		
		return categoryMapper.selectParentCategoryId(categoryId);
	}

	@Override
	public ServerResponse<List<Category>> pageList(Integer page, Integer limit, Category name) {
		PageHelper.startPage(page,limit);
		System.out.println(name);
		List<Category> list = categoryMapper.pageList(name);
		for (Category category : list) {
		    System.out.println(category);
		}
		Integer count = (int) ((Page)list).getTotal();
		
		return ServerResponse.createSuccess("查询成功",count,list);
	}

	@Override
	public ServerResponse deleteById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse add(Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerResponse deleteAll(String ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category selectById(Integer id) {
		return categoryMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<Category> selectCategory(){
		List<Category> list = categoryMapper.selectTopCategory();
		return list;
	}

	@Override
	public List<Category> selectTopCategoryList() {
		return categoryMapper.selectTopCategory();
	}

	@Override
	public List<Category> selectSecondCategoryList() {
		return categoryMapper.selectSecondCategoryList();
	}

}
