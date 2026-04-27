<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.entity.Textbook" %>
<%@ page import="com.example.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>教材列表</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7f6; margin: 0; padding: 0; }
        .header { background: #007bff; color: #fff; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header h1 { margin: 0; font-size: 20px; }
        .nav a { color: #fff; text-decoration: none; margin-left: 20px; font-size: 14px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 1100px; margin: 30px auto; padding: 0 20px; }
        .card { background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05); padding: 25px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #f8f9fa; color: #333; font-weight: 600; }
        tr:hover { background: #f8f9fa; }
        .btn { padding: 6px 14px; border-radius: 4px; text-decoration: none; font-size: 13px; display: inline-block; }
        .btn-primary { background: #007bff; color: #fff; }
        .btn-primary:hover { background: #0056b3; }
        .stock { color: #28a745; font-weight: 600; }
        .price { color: #e74c3c; font-weight: 600; }
        .user-info { font-size: 14px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>校园教材预订系统</h1>
        <div class="nav">
            <span class="user-info">
                <% User user = (User) session.getAttribute("user");
                   if (user != null) { %>
                    欢迎，<%= user.getName() %>
                <% } %>
            </span>
            <a href="${pageContext.request.contextPath}/textbookList">教材列表</a>
            <a href="${pageContext.request.contextPath}/myOrder">我的预订</a>
            <a href="${pageContext.request.contextPath}/logout">退出登录</a>
        </div>
    </div>
    <div class="container">
        <div class="card">
            <h2>教材列表</h2>
            <table>
                <tr>
                    <th>ID</th>
                    <th>书名</th>
                    <th>作者</th>
                    <th>出版社</th>
                    <th>ISBN</th>
                    <th>所属课程</th>
                    <th>价格</th>
                    <th>库存</th>
                    <th>操作</th>
                </tr>
                <% List<Textbook> textbooks = (List<Textbook>) request.getAttribute("textbooks");
                   if (textbooks != null) {
                       for (Textbook t : textbooks) { %>
                <tr>
                    <td><%= t.getId() %></td>
                    <td><%= t.getTitle() %></td>
                    <td><%= t.getAuthor() %></td>
                    <td><%= t.getPublisher() %></td>
                    <td><%= t.getIsbn() %></td>
                    <td><%= t.getCourse() %></td>
                    <td class="price">￥<%= String.format("%.2f", t.getPrice()) %></td>
                    <td class="stock"><%= t.getStock() %></td>
                    <td>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/bookOrder?textbookId=<%= t.getId() %>">预订</a>
                    </td>
                </tr>
                <%   }
                   } %>
            </table>
        </div>
    </div>
</body>
</html>
