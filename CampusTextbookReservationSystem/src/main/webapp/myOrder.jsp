<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.entity.BookOrder" %>
<%@ page import="com.example.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的预订</title>
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
        .btn { padding: 5px 12px; border-radius: 4px; text-decoration: none; font-size: 12px; display: inline-block; margin-right: 5px; }
        .btn-warning { background: #ffc107; color: #333; }
        .btn-warning:hover { background: #e0a800; }
        .btn-danger { background: #dc3545; color: #fff; }
        .btn-danger:hover { background: #c82333; }
        .status-pending { color: #007bff; font-weight: 600; }
        .status-confirmed { color: #28a745; font-weight: 600; }
        .status-cancelled { color: #6c757d; font-weight: 600; text-decoration: line-through; }
        .empty { text-align: center; padding: 40px; color: #888; }
        .total { text-align: right; margin-top: 15px; font-size: 14px; color: #555; }
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
            <h2>我的预订</h2>
            <% List<BookOrder> orders = (List<BookOrder>) request.getAttribute("orders");
               if (orders != null && !orders.isEmpty()) { %>
            <table>
                <tr>
                    <th>订单号</th>
                    <th>教材名称</th>
                    <th>单价</th>
                    <th>数量</th>
                    <th>总价</th>
                    <th>状态</th>
                    <th>预订时间</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                <% double grandTotal = 0;
                   for (BookOrder order : orders) {
                       double total = order.getTotalPrice();
                       if (!"cancelled".equals(order.getStatus())) {
                           grandTotal += total;
                       }
                       String statusClass = "status-pending";
                       String statusText = "待确认";
                       if ("confirmed".equals(order.getStatus())) { statusClass = "status-confirmed"; statusText = "已确认"; }
                       else if ("cancelled".equals(order.getStatus())) { statusClass = "status-cancelled"; statusText = "已取消"; }
                %>
                <tr>
                    <td><%= order.getId() %></td>
                    <td><%= order.getTextbook() != null ? order.getTextbook().getTitle() : "-" %></td>
                    <td>￥<%= order.getTextbook() != null ? String.format("%.2f", order.getTextbook().getPrice()) : "-" %></td>
                    <td><%= order.getQuantity() %></td>
                    <td class="price">￥<%= String.format("%.2f", total) %></td>
                    <td class="<%= statusClass %>"><%= statusText %></td>
                    <td><%= order.getOrderTime() %></td>
                    <td><%= order.getRemark() != null ? order.getRemark() : "-" %></td>
                    <td>
                        <% if (!"cancelled".equals(order.getStatus())) { %>
                            <a class="btn btn-warning" href="${pageContext.request.contextPath}/orderUpdate?orderId=<%= order.getId() %>">修改</a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/orderCancel?orderId=<%= order.getId() %>">取消</a>
                        <% } else { %>
                            <span style="color:#888;font-size:12px;">无</span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
            </table>
            <div class="total">有效订单总金额：<span class="price">￥<%= String.format("%.2f", grandTotal) %></span></div>
            <% } else { %>
                <div class="empty">暂无预订记录，快去教材列表预订吧！</div>
                <div style="text-align:center;margin-top:10px;">
                    <a href="${pageContext.request.contextPath}/textbookList" class="btn btn-primary" style="background:#007bff;color:#fff;">去预订</a>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
