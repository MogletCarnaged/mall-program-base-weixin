package com.mall.util;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

public class ServletUtils {
    public static void renderHttpResult(HttpServletResponse resp,HttpResult httpResult) {
        resp.setContentType("application/json;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        try {
            Writer writer = resp.getWriter();
            writer.write(JSON.toJSONString(httpResult));
            writer.flush();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static Object convertStringToFieldType(String value, Class<?> type) {
        if(type == String.class) {return value;}
        if(type == Integer.class || type == int.class) { return Integer.parseInt(value); }
        if(type == Long.class || type == long.class) { return Long.parseLong(value); }
        if(type == Boolean.class || type ==boolean.class) { return Boolean.parseBoolean(value); }
        // 还可以根据将来的需要进行自定义的类型映射扩展
        throw new IllegalArgumentException("Unsupported type: " + type.getName() + "conver!");
    }
    public static <T> T mapObjectFromRequestParameter(HttpServletRequest request, Class<T> clazz) {
        try {
            // 通过反射创建类的对象
            T  instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();              // 反射得到字段的名字
                String value = request.getParameter(name);  // 用反射得到的字段名去从request的url的query中取叁数值（可能取得到，也可能取不到）
                if(value != null && !value.trim().isEmpty()) {
                    Class<?> type = field.getType();          // 反射得到字段声明时的具体类型
                    Object fieldValue = convertStringToFieldType(value, type);
                    field.set(instance,fieldValue);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map Object from request parameter!");
        }
    }
    public static <T> T mapObjectFromRequestBody(HttpServletRequest req,Class<T> clazz) {
        StringBuilder body = new StringBuilder();
        try(BufferedReader reader = req.getReader()) {
            String line;
            while((line = reader.readLine()) != null) {
                body.append(line);
            }
            return (T) JSON.parseObject(body.toString(), clazz);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
