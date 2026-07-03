package com.mall.servlet;

import com.mall.model.Category;
import com.mall.service.CategoryService;
import com.mall.service.Impl.CategoryServiceImpl;
import com.mall.util.HttpResult;
import com.mall.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/categories")
public class CategoryServlet extends HttpServlet {
    private static CategoryService categoryService=new CategoryServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categoryList= categoryService.getall();
        HttpResult httpResult=HttpResult.success(categoryList);
        ServletUtils.renderHttpResult(resp,httpResult);
    }
}
