package com.example.servlet;

import com.example.dao.DataStore;
import com.example.entity.BookOrder;
import com.example.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "myOrderServlet", value = "/myOrder")
public class MyOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        List<BookOrder> orders = DataStore.findOrdersByUserId(user.getId());

        // 填充关联信息
        for (BookOrder order : orders) {
            order.setUser(user);
            order.setTextbook(DataStore.findTextbookById(order.getTextbookId()));
        }

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/myOrder.jsp").forward(request, response);
    }
}
