# Mall Program — 微信小程序商城（全栈项目教学）

> 基于 **微信小程序** + **Java Servlet** + **MySQL** 的全栈商城项目，适合前后端分离开发入门学习。

---

## 📋 目录

- [整体架构](#-整体架构前后端分离)
- [前端 — mall-miniprogram（微信小程序）](#-前端--mall-miniprogram微信小程序)
- [后端 — mall-server（Java Servlet）](#-后端--mall-serverjava-servlet)
- [前后端交互流程](#-前后端交互流程)
- [如何新建类似项目](#-如何新建类似项目)
- [技术栈](#-技术栈)
- [快速启动](#-快速启动)

---

## 🏗 整体架构：前后端分离

```
┌──────────────────────┐       HTTP/JSON        ┌──────────────────────┐
│   微信小程序 前端       │ ────────────────────→  │   Java 后端服务       │
│   (mall-miniprogram)  │ ←──────────────────── │   (mall-server)       │
│   用户界面 + 交互       │       JSON 响应        │   业务逻辑 + 数据      │
└──────────────────────┘                         └──────────┬───────────┘
                                                             │
                                                      ┌──────▼───────┐
                                                      │  MySQL 数据库  │
                                                      └──────────────┘
```

---

## 📁 前端 — mall-miniprogram（微信小程序）

### 1️⃣ 项目入口文件

```
mall-miniprogram/
├── app.json      # 小程序配置文件
├── app.js        # 全局逻辑
├── app.wxss      # 全局样式
├── pages/        # 页面目录
├── utils/        # 工具目录
└── images/       # 图片资源
```

**`app.json` 核心配置：**

```json
{
  "pages": ["pages/index/index", "pages/login/login", ...],
  "entryPagePath": "pages/login/login",   // 启动入口页（先登录）
  "tabBar": {
    "list": [
      { "pagePath": "pages/index/index", "text": "首页" },
      { "pagePath": "pages/category/category", "text": "分类" },
      { "pagePath": "pages/cart/cart", "text": "购物车" },
      { "pagePath": "pages/profile/profile", "text": "个人" }
    ]
  }
}
```

> **📖 知识点：** `app.json` 是小程序的"门面"，所有页面必须先在这里注册才能使用，底部 Tab 栏也在这里配置。

### 2️⃣ 页面结构

每个页面由 **4 个文件** 组成，各司其职：

```
pages/login/
├── login.js      # 页面逻辑（数据、事件处理、网络请求）
├── login.wxml    # 页面结构（类似 HTML）
├── login.wxss    # 页面样式（类似 CSS）
└── login.json    # 页面单独配置
```

**7 个页面功能一览：**

| 页面 | 功能 |
|------|------|
| `login/login` | 微信授权登录，获取用户身份 |
| `index/index` | 首页：商品展示、分类导航、功能入口 |
| `category/category` | 分类页：左侧分类列表 + 右侧商品 |
| `list/list` | 商品列表：按分类筛选展示 |
| `search/search` | 搜索页 |
| `cart/cart` | 购物车 |
| `profile/profile` | 个人中心、服务列表 |

### 3️⃣ 核心工具封装 — utils/

```
utils/
├── request.js      ★ 核心：封装 HTTP 网络请求
├── wxUtils.js       封装微信 wx.login 为 Promise
└── util.js          时间格式化等小工具
```

**`request.js` — 统一请求管理：**

```javascript
export const request = function(userConfig) {
    // ① 自动拼接 API 地址前缀
    userConfig.url = "http://localhost:8082/api/v1" + userConfig.url;
    // ② 默认请求方式
    userConfig.method = userConfig.method || 'GET';
    return new Promise((resolve, reject) => {
        wx.request({
            ...userConfig,
            success: (result) => {
                if(result.statusCode === 200) {
                    const { code, data } = result.data;
                    if(code === 200) resolve(data);  // ③ 只返回数据部分
                }
            }
        })
    })
}
```

> **💡 设计思想：** 统一管理 baseURL、统一处理响应、返回 Promise 支持 async/await。页面代码只需关心业务数据，不需要重复处理 HTTP 细节。

---

## 📁 后端 — mall-server（Java Servlet）

### 三层架构

```
┌──────────────────────────────────────────────┐
│           Controller 层（servlet/）             │
│      接收 HTTP 请求、解析参数、返回 JSON          │
│    CategoryServlet / ProductServlet / ...      │
└───────────────────┬──────────────────────────┘
                    │
┌───────────────────▼──────────────────────────┐
│            Service 层（service/）               │
│         业务逻辑处理、事务管理                    │
│     CategoryServiceImpl / ProductServiceImpl   │
└───────────────────┬──────────────────────────┘
                    │
┌───────────────────▼──────────────────────────┐
│             DAO 层（dao/）                      │
│          数据库访问、SQL 执行                    │
│      CategoryDaoImpl / ProductDaoImpl          │
└───────────────────┬──────────────────────────┘
                    │
             ┌──────▼──────┐
             │ MySQL 数据库  │
             └─────────────┘
```

### 1️⃣ 数据源配置 — common/Global.java

```java
public class Global {
    private static DataSource dataSource = null;

    public static DataSource getDataSource() {
        if (dataSource == null) {   // 单例：只创建一次连接池
            Driver driver = new com.mysql.jdbc.Driver();
            dataSource = new SimpleDriverDataSource(driver, URL, USER, PASSWORD);
        }
        return dataSource;
    }
}
```

> **📖 知识点：** 使用 Spring JDBC 的 `SimpleDriverDataSource` 管理数据库连接，避免每次请求都创建新连接。

### 2️⃣ Controller 层 — servlet/

**使用注解配置 URL 映射（无需 web.xml）：**

```java
@WebServlet("/api/v1/categories")       // ← 注解声明 URL 路径
public class CategoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // ① 调用 Service 层
        List<Category> list = categoryService.getall();
        // ② 封装统一响应
        HttpResult result = HttpResult.success(list);
        // ③ 返回 JSON
        ServletUtils.renderHttpResult(resp, result);
    }
}
```

**统一的响应格式：**

```java
// 所有接口返回格式统一，前端只需判断 code
public class HttpResult {
    private Integer code;   // 200=成功
    private String msg;     // 提示信息
    private Object data;    // 实际数据
}
```

**请求处理工具 ServletUtils：**

| 方法 | 作用 |
|------|------|
| `renderHttpResult()` | Java 对象 → 输出 JSON 响应 |
| `mapObjectFromRequestBody()` | 请求 Body（JSON）→ Java 对象 |
| `mapObjectFromRequestParameter()` | URL 查询参数 → Java 对象 |

### 3️⃣ Service 层 — service/

```java
// 接口与实现分离（面向接口编程）
public interface CategoryService {
    List<Category> getall();
}

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    public List<Category> getall() {
        return categoryDao.getAll();  // 调用 DAO 层
    }
}
```

### 4️⃣ DAO 层 — dao/

```java
public class CategoryDaoImpl implements CategoryDao {
    public List<Category> getAll() {
        // Spring JDBC 的 JdbcTemplate 简化数据库操作
        JdbcTemplate jt = new JdbcTemplate(Global.getDataSource());
        String sql = "select * from category order by id asc";
        // BeanPropertyRowMapper 自动映射数据库字段到 Java 对象
        RowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);
        return jt.query(sql, rowMapper);
    }
}
```

> **📖 知识点：** `BeanPropertyRowMapper` 会根据数据库字段名和 Java 属性名自动匹配（如 `category_id` → `categoryId`），省去手动封装结果集。

### 5️⃣ 数据模型 — model/

```java
@Getter @Setter @NoArgsConstructor   // Lombok 自动生成 getter/setter/构造方法
public class Category {
    private Integer id;
    private String name;
}
```

---

## 📡 前后端交互流程

以"获取分类列表"为例，完整走一遍请求链路：

```
① 小程序打开分类页
         │
② category.js 发起请求
   request({ url: "/categories" })
         │
③ request.js 拼接完整地址
   "http://localhost:8082/api/v1/categories"
         │
         │  GET 请求
         ▼
④ CategoryServlet.doGet() ←────────────────
         │
⑤ → CategoryService.getall()
         │
⑥ → CategoryDaoImpl.getAll()
         │
⑦ → JdbcTemplate.query("select * from category")
         │
⑧ → MySQL 查询并返回结果
         │
⑨ 数据逐层返回：DAO → Service → Controller
         │
⑩ ServletUtils.renderHttpResult() → JSON
         │    { "code": 200, "data": [...] }
         │  JSON 响应
         ▼
⑪ 小程序收到数据 → setData() → 页面渲染 ✅
```

---

## 🛠 如何新建类似项目

### 后端步骤

```bash
# ① 创建 Maven 项目（打包方式: war）
# ② pom.xml 添加依赖
```
```xml
<dependencies>
    <!-- Servlet API -->
    <dependency>
        <groupId>org.apache.tomcat</groupId>
        <artifactId>tomcat-servlet-api</artifactId>
        <version>11.0.2</version>
    </dependency>
    <!-- Spring JDBC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>6.2.7</version>
    </dependency>
    <!-- MySQL 驱动 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.3.0</version>
    </dependency>
    <!-- JSON 处理 -->
    <dependency>
        <groupId>com.alibaba.fastjson2</groupId>
        <artifactId>fastjson2</artifactId>
        <version>2.0.57</version>
    </dependency>
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.38</version>
    </dependency>
</dependencies>
```

```bash
# ③ 按 Controller → Service → DAO → Model 顺序开发
# ④ 部署到 Tomcat 11
```

### 前端步骤

```bash
# ① 微信开发者工具创建项目
# ② 创建 utils/request.js 封装请求
# ③ 创建页面目录 pages/xxx/（4 个文件一组）
# ④ 配置 app.json 注册页面和 Tab 栏
```

### 设计原则总结

| 原则 | 说明 |
|------|------|
| **统一响应格式** | 所有接口返回 `{code, msg, data}` |
| **三层架构** | Controller 接收请求 / Service 处理业务 / DAO 操作数据库 |
| **请求封装** | 前端统一管理 baseURL，页面只关心数据 |
| **面向接口编程** | Service 和 DAO 层都定义接口，实现类可替换 |

---

## 📚 技术栈

| 模块 | 技术 | 版本 |
|------|------|------|
| 前端 | 微信小程序原生（WXML + WXSS + JS） | - |
| UI 组件 | Vant Weapp | - |
| 后端 | Java + Tomcat (Servlet API) | 21 + 11 |
| 数据库 | MySQL + Spring JDBC | 8.3 + 6.2.7 |
| JSON | FastJSON | 2.0.57 |
| 工具 | Lombok | 1.18.38 |
| 构建 | Maven + Maven Wrapper | - |

---

## 🚀 快速启动

### 后端
```bash
cd mall-server
./mvnw clean package
# 将生成的 mall-server.war 部署至 Tomcat 11
```

### 前端
用微信开发者工具打开 `mall-miniprogram` 目录即可。
