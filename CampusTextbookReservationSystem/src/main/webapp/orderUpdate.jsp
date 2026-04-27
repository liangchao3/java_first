<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.BookOrder" %>
<%@ page import="com.example.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改预订</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7f6; margin: 0; padding: 0; }
        .header { background: #007bff; color: #fff; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header h1 { margin: 0; font-size: 20px; }
        .nav a { color: #fff; text-decoration: none; margin-left: 20px; font-size: 14px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 600px; margin: 30px auto; padding: 0 20px; }
        .card { background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05); padding: 30px; }
        h2 { margin-top: 0; color: #333; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 6px; color: #555; font-size: 14px; }
        input[type="text"], input[type="number"], textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; font-family: Arial, sans-serif; }
        textarea { resize: vertical; height: 80px; }
        .info { background: #f8f9fa; padding: 15px; border-radius: 4px; margin-bottom: 20px; }
        .info p { margin: 6px 0; font-size: 14px; }
        .info span { font-weight: 600; color: #333; }
        .btn { padding: 10px 20px; border-radius: 4px; text-decoration: none; font-size: 14px; border: none; cursor: pointer; display: inline-block; }
        .btn-primary { background: #007bff; color: #fff; }
        .btn-primary:hover { background: #0056b3; }
        .btn-default { background: #6c757d; color: #fff; margin-left: 10px; }
        .btn-default:hover { background: #5a6268; }
        .error { color: #e74c3c; margin-bottom: 15px; font-size: 14px; }
        .price { color: #e74c3c; font-weight: 600; }
    </style>
</head>
<body>
    <div class="header">
        <h1>校园教材预订系统</h1>
        <div class="nav">
            <span style="font-size:14px;">
                <% User user = (User) session.getAttribute("user");
                   if (user != null) { %>欢迎，<%= user.getName() %><% } %>
            </span>
            <a href="${pageContext.request.contextPath}/textbookList">教材列表</a>
            <a href="${pageContext.request.contextPath}/myOrder">我的预订</a>
            <a href="${pageContext.request.contextPath}/logout">退出登录</a>
        </div>
    </div>
    <div class="container">
        <div class="card">
            <h2>修改预订</h2>
            <% BookOrder order = (BookOrder) request.getAttribute("order");
               if (order != null) { %>
                <div class="info">
                    <p><span>订单号：</span><%= order.getId() %></p>
                    <p><span>教材：</span><%= order.getTextbook() != null ? order.getTextbook().getTitle() : "-" %></p>
                    <p><span>单价：</span><span class="price">￥<%= order.getTextbook() != null ? String.format("%.2f", order.getTextbook().getPrice()) : "-" %></span></p>
                </div>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error"><%= request.getAttribute("error") %></div>
                <% } %>
                <form action="${pageContext.request.contextPath}/orderUpdate" method="post">
                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                    <div class="form-group">
                        <label>预订数量</label>
                        <input type="number" name="quantity" min="1"
                               max="<%= (order.getTextbook() != null ? order.getTextbook().getStock() : 0) + order.getQuantity() %>"
                               value="<%= order.getQuantity() %>" required>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea name="remark"><%= order.getRemark() != null ? order.getRemark() : "" %></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">保存修改</button>
                    <a href="${pageContext.request.contextPath}/myOrder" class="btn btn-default">返回</a>
                </form>
            <% } else { %>
                <p>订单不存在。</p>
                <a href="${pageContext.request.contextPath}/myOrder" class="btn btn-default">返回</a>
            <% } %>
        </div>
    </div>
</body>
</html>
