package com.mall.service.Impl;


import com.mall.dao.ProductDao;
import com.mall.dao.Impl.ProductDaoImpl;
import com.mall.model.Product;
import com.mall.service.ProductService;
import com.mall.util.PageInfo;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao=new ProductDaoImpl();
    @Override
    public List<Product> get(Product product, PageInfo pageInfo) {
        return productDao.get(product, pageInfo);
    }
}