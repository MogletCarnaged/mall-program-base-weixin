package com.mall.servlet;

import com.mall.model.Product;
import com.mall.service.ProductService;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.util.HttpResult;
import com.mall.util.PageInfo;
import com.mall.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/api/v1/products")
public class ProductServlet extends HttpServlet {
    private static final ProductService productService=new ProductServiceImpl();
    //反射是对说明的说明
    @Override
    //从url的query请求中拿到想要的字段
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*        Product product=new Product();*/
        Product product= ServletUtils.mapObjectFromRequestParameter(req,Product.class);
        PageInfo pageInfo=ServletUtils.mapObjectFromRequestParameter(req,PageInfo.class);
        List<Product> productList=productService.get(product,pageInfo);
        HttpResult httpresult =HttpResult.success(productList);
        ServletUtils.renderHttpResult(resp,httpresult);
    }
}
