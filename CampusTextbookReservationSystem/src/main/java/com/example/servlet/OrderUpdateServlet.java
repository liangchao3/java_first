package com.example.servlet;

import com.example.dao.DataStore;
import com.example.entity.BookOrder;
import com.example.entity.Textbook;
import com.example.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "orderUpdateServlet", value = "/orderUpdate")
public class OrderUpdateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        BookOrder order = DataStore.findOrderById(orderId);

        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/myOrder");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (order.getUserId() != user.getId()) {
            response.sendRedirect(request.getContextPath() + "/myOrder");
            return;
        }

        if ("cancelled".equals(order.getStatus())) {
            request.setAttribute("error", "已取消的预订不能修改");
            response.sendRedirect(request.getContextPath() + "/myOrder");
            return;
        }

        order.setTextbook(DataStore.findTextbookById(order.getTextbookId()));
        request.setAttribute("order", order);
        request.getRequestDispatcher("/orderUpdate.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String remark = request.getParameter("remark");

        BookOrder order = DataStore.findOrderById(orderId);
        if (order == null || "cancelled".equals(order.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/myOrder");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (order.getUserId() != user.getId()) {
            response.sendRedirect(request.getContextPath() + "/myOrder");
            return;
        }

        Textbook textbook = DataStore.findTextbookById(order.getTextbookId());
        int oldQuantity = order.getQuantity();
        int stockChange = oldQuantity - quantity;

        if (quantity <= 0 || stockChange < -textbook.getStock()) {
            request.setAttribute("error", "预订数量无效或库存不足");
            order.setTextbook(textbook);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/orderUpdate.jsp").forward(request, response);
            return;
        }

        order.setQuantity(quantity);
        order.setRemark(remark);
        DataStore.updateOrder(order);
        textbook.setStock(textbook.getStock() + stockChange);

        response.sendRedirect(request.getContextPath() + "/myOrder");
    }
}
