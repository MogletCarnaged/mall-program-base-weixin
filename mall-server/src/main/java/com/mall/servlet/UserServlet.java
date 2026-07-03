package com.mall.servlet;

import com.alibaba.fastjson2.JSONObject;
import com.mall.model.User;
import com.mall.service.Impl.UserServiceImpl;
import com.mall.service.UserService;
import com.mall.util.HttpResult;
import com.mall.util.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/v1/users/login")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private String getOpenid(String code) throws Exception {
        // 参数及请求url准备
        String WX_API_URL = "https://api.weixin.qq.com/sns/jscode2session";
        String APP_ID = "wx0cd34b7b29003b76";
        String APP_SECRET = "e196fe05f0e56ba76c82e69be05eb9e4";
        String wxParams = "appid=" + APP_ID +
                "&secret=" + APP_SECRET +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String url = WX_API_URL + "?" + wxParams;
        // 向腾讯服务器发起请求code换openid
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        JSONObject wxJson = JSONObject.parseObject(response.toString());
        if(wxJson.containsKey("errcode")) { throw new Exception(wxJson.getString("errmsg")); }
        return wxJson.getString("openid");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = ServletUtils.mapObjectFromRequestBody(req, User.class);
            user.setOpenid(getOpenid(user.getCode()));
            user = userService.loginAndRegister(user);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("token", user == null ? "" : "token令牌");
            data.put("user", user);
            HttpResult result = HttpResult.success(data);
            ServletUtils.renderHttpResult(resp, result);
        } catch(Exception e) {
            throw  new ServletException(e);
        }
    }
}
