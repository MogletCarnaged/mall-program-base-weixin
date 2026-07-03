package com.mall.service;



import com.mall.model.Product;
import com.mall.util.PageInfo;

import java.util.List;

public interface ProductService {
    List<Product> get(Product product, PageInfo pageInfo);
}
