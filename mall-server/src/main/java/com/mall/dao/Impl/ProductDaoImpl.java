package com.mall.dao.Impl;


import com.mall.common.Global;
import com.mall.dao.ProductDao;
import com.mall.model.Product;
import com.mall.util.PageInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    @Override
    public List<Product> get(Product product, PageInfo pageInfo) {
        List<Object> items = new ArrayList<Object>();
        String sql = "select * from product";
        String where = " where 1=1 ";
        if (product.getId() != null) {
            where += " and id = ?";//不能拼接，防止拼接造成的安全性泄露
            items.add(product.getId());
        }
        if (product.getCategoryId() != null) {
            where += " and category_id = ?";
            items.add(product.getCategoryId());
        }
        if (product.getName() != null) {
            where += " and name like ?";
            items.add("%" + product.getName() + "%");
            where += " limit ?, ?";
            items.add(pageInfo.getOffset());//跳过多少页
            items.add(pageInfo.getLimit());
        }
        //limit?,?第一个指跳过多少条
        sql+=where;
        JdbcTemplate jt=new JdbcTemplate(Global.getDataSource());
        RowMapper<Product> rowMapper=new BeanPropertyRowMapper<>(Product.class);//泛型类需要补充尖括号，其中的内容有时需要填写
        return jt.query(sql,rowMapper,items.toArray());
    }
}

