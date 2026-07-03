package com.mall.dao.Impl;

import com.mall.common.Global;
import com.mall.dao.ServiceDao;
import com.mall.model.Service;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ServiceDaoImpl implements ServiceDao {
    @Override
    public List<Service> getAll() {
        String sql = "select * from service order by id asc";
        JdbcTemplate jt = new JdbcTemplate(Global.getDataSource());
        RowMapper<Service> rowMapper = new BeanPropertyRowMapper<Service>(Service.class);
        return jt.query(sql,rowMapper);
    }
}
