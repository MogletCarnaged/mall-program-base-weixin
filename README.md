# Mall Program - 微信小程序商城

基于微信小程序 + Java Servlet 的全栈商城项目。

## 项目结构

```
mall-project/
├── mall-miniprogram/       # 前端 - 微信小程序
│   ├── pages/              # 页面（首页、分类、购物车、个人中心等）
│   ├── utils/              # 工具类（请求封装等）
│   ├── images/             # 图片资源
│   └── app.js / app.json   # 小程序入口配置
│
└── mall-server/            # 后端 - Java Servlet
    ├── src/                # 源码
    │   └── main/java/com/mall/
    │       ├── servlet/    # 控制器层
    │       ├── service/    # 业务逻辑层
    │       ├── dao/        # 数据访问层
    │       ├── model/      # 实体类
    │       └── util/       # 工具类
    └── pom.xml             # Maven 构建配置
```

## 技术栈

| 模块 | 技术 |
|------|------|
| 前端 | 微信小程序原生（WXML + WXSS + JS）|
| UI 组件 | Vant Weapp |
| 后端 | Java 21 + Tomcat 11 (Servlet API) |
| 数据库 | MySQL + Spring JDBC |
| 构建工具 | Maven + Maven Wrapper |

## 快速启动

### 后端
```bash
cd mall-server
./mvnw clean package
# 将 war 包部署至 Tomcat 11
```

### 前端
用微信开发者工具打开 `mall-miniprogram` 目录即可。
