package com.mall.dao;


import com.mall.model.Product;
import com.mall.util.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
public interface ProductDao {
    public List<Product> get(Product product, PageInfo page);
}
