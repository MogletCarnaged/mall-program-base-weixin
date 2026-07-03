package com.mall.dao.Impl;

import com.mall.common.Global;
import com.mall.dao.FuncEntryDao;
import com.mall.model.FuncEntry;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class                                                                        FuncEntryDaoImpl implements FuncEntryDao {

    @Override
    public List<FuncEntry> getAllActive() {
        String sql = "SELECT * FROM func_entry WHERE is_active = 1 ORDER BY sort_order ASC";
        JdbcTemplate jt = new JdbcTemplate(Global.getDataSource());
        RowMapper<FuncEntry> rowMapper = new BeanPropertyRowMapper<>(FuncEntry.class);
        return jt.query(sql, rowMapper);
    }

    // 测试方法
    public static void main(String[] args) {
        FuncEntryDao dao = new FuncEntryDaoImpl();
        List<FuncEntry> entries = dao.getAllActive();
        System.out.println(entries);
    }
}
