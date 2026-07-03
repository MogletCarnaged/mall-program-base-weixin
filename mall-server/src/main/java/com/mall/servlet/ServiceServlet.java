package com.mall.servlet;

import com.mall.model.Service;
import com.mall.service.Impl.ServiceServiceImpl;
import com.mall.service.ServiceService;
import com.mall.util.HttpResult;
import com.mall.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/services")
public class ServiceServlet extends HttpServlet {
    private static final ServiceService serviceService = new ServiceServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Service> serviceList = serviceService.getAll();
        HttpResult httpResult = HttpResult.success(serviceList);
        ServletUtils.renderHttpResult(resp,httpResult);
    }
}
