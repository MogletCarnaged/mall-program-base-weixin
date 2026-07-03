package com.mall.service.Impl;

import com.mall.dao.Impl.ServiceDaoImpl;
import com.mall.dao.ServiceDao;
import com.mall.model.Service;
import com.mall.service.ServiceService;

import java.util.List;

public class ServiceServiceImpl implements ServiceService {
    private final ServiceDao serviceDao = new ServiceDaoImpl();
    @Override
    public List<Service> getAll() {
        return serviceDao.getAll();
    }
}
