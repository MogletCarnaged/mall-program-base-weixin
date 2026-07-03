package com.mall.service.Impl;

import com.mall.dao.CategoryDao;
import com.mall.dao.Impl.CategoryDaoImpl;
import com.mall.model.Category;
import com.mall.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private static final CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public List<Category> getall() {
        return categoryDao.getAll();
    }
}
