package com.mall.servlet;

import com.mall.dao.FuncEntryDao;
import com.mall.dao.Impl.FuncEntryDaoImpl;
import com.mall.model.FuncEntry;
import com.mall.util.HttpResult;
import com.mall.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/func-entries")
public class FuncEntryServlet extends HttpServlet {
    private static final FuncEntryDao funcEntryDao = new FuncEntryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FuncEntry> funcEntries = funcEntryDao.getAllActive();
        HttpResult httpResult = HttpResult.success(funcEntries);
        ServletUtils.renderHttpResult(resp, httpResult);
    }
}