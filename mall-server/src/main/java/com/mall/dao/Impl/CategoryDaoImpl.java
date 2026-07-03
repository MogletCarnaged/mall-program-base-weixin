package com.mall.dao.Impl;

import com.mall.common.Global;
import com.mall.dao.CategoryDao;
import com.mall.model.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> getAll() {
        //获取数据源
        String sql="select * from category order by id asc";
        JdbcTemplate jt=new JdbcTemplate(Global.getDataSource());
        RowMapper<Category> rowMapper=new BeanPropertyRowMapper<>(Category.class);//泛型类需要补充尖括号，其中的内容有时需要填写
        return jt.query(sql,rowMapper);
    }
}
