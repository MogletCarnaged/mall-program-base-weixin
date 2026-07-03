package com.mall.service;

import com.mall.dao.ServiceDao;
import com.mall.model.Service;

import java.util.List;

public interface ServiceService {
    List<Service> getAll();
}
