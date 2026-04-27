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

@WebServlet(name = "orderCancelServlet", value = "/orderCancel")
public class OrderCancelServlet extends HttpServlet {

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

        order.setTextbook(DataStore.findTextbookById(order.getTextbookId()));
        request.setAttribute("order", order);
        request.getRequestDispatcher("/orderCancel.jsp").forward(request, response);
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
            request.setAttribute("error", "该预订已经取消");
            request.getRequestDispatcher("/orderCancel.jsp").forward(request, response);
            return;
        }

        // 恢复库存
        Textbook textbook = DataStore.findTextbookById(order.getTextbookId());
        if (textbook != null) {
            textbook.setStock(textbook.getStock() + order.getQuantity());
        }

        DataStore.cancelOrder(orderId);
        response.sendRedirect(request.getContextPath() + "/myOrder");
    }
}
