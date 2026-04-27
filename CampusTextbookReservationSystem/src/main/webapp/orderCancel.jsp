<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.example.entity.BookOrder" %>
<%@ page import="com.example.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>取消预订</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f7f6; margin: 0; padding: 0; }
        .header { background: #007bff; color: #fff; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .header h1 { margin: 0; font-size: 20px; }
        .nav a { color: #fff; text-decoration: none; margin-left: 20px; font-size: 14px; }
        .nav a:hover { text-decoration: underline; }
        .container { max-width: 600px; margin: 30px auto; padding: 0 20px; }
        .card { background: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05); padding: 30px; text-align: center; }
        h2 { margin-top: 0; color: #333; }
        .info { background: #f8f9fa; padding: 20px; border-radius: 4px; margin: 20px 0; text-align: left; }
        .info p { margin: 8px 0; font-size: 14px; }
        .info span { font-weight: 600; color: #333; }
        .btn { padding: 10px 25px; border-radius: 4px; text-decoration: none; font-size: 14px; border: none; cursor: pointer; display: inline-block; margin: 5px; }
        .btn-danger { background: #dc3545; color: #fff; }
        .btn-danger:hover { background: #c82333; }
        .btn-default { background: #6c757d; color: #fff; }
        .btn-default:hover { background: #5a6268; }
        .error { color: #e74c3c; margin-bottom: 15px; font-size: 14px; }
        .warn { color: #856404; background: #fff3cd; padding: 12px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; }
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
            <h2>取消预订确认</h2>
            <% BookOrder order = (BookOrder) request.getAttribute("order");
               if (order != null) { %>
                <div class="warn">确定要取消以下预订吗？取消后库存将恢复。</div>
                <div class="info">
                    <p><span>订单号：</span><%= order.getId() %></p>
                    <p><span>教材：</span><%= order.getTextbook() != null ? order.getTextbook().getTitle() : "-" %></p>
                    <p><span>数量：</span><%= order.getQuantity() %></p>
                    <p><span>总价：</span><span class="price">￥<%= String.format("%.2f", order.getTotalPrice()) %></span></p>
                    <p><span>预订时间：</span><%= order.getOrderTime() %></p>
                </div>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error"><%= request.getAttribute("error") %></div>
                <% } %>
                <form action="${pageContext.request.contextPath}/orderCancel" method="post" style="display:inline;">
                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                    <button type="submit" class="btn btn-danger">确认取消</button>
                </form>
                <a href="${pageContext.request.contextPath}/myOrder" class="btn btn-default">返回</a>
            <% } else { %>
                <p>订单不存在。</p>
                <a href="${pageContext.request.contextPath}/myOrder" class="btn btn-default">返回</a>
            <% } %>
        </div>
    </div>
</body>
</html>
