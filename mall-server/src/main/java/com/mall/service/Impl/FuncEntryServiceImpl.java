package com.mall.service.Impl;

import com.mall.dao.FuncEntryDao;
import com.mall.dao.Impl.FuncEntryDaoImpl;
import com.mall.model.FuncEntry;
import com.mall.service.FuncEntryService;

import java.util.List;

public class FuncEntryServiceImpl implements FuncEntryService {
    private final FuncEntryDao funcEntryDao = new FuncEntryDaoImpl();

    @Override
    public List<FuncEntry> getAllActive() {
        return funcEntryDao.getAllActive();
    }
}
