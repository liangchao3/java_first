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

@WebServlet(name = "bookOrderServlet", value = "/bookOrder")
public class BookOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String textbookIdStr = request.getParameter("textbookId");
        if (textbookIdStr != null) {
            int textbookId = Integer.parseInt(textbookIdStr);
            Textbook textbook = DataStore.findTextbookById(textbookId);
            request.setAttribute("textbook", textbook);
        }

        request.getRequestDispatcher("/bookOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        int textbookId = Integer.parseInt(request.getParameter("textbookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String remark = request.getParameter("remark");

        Textbook textbook = DataStore.findTextbookById(textbookId);
        if (textbook == null) {
            request.setAttribute("error", "教材不存在");
            request.getRequestDispatcher("/bookOrder.jsp").forward(request, response);
            return;
        }

        if (quantity <= 0 || quantity > textbook.getStock()) {
            request.setAttribute("error", "预订数量无效或库存不足");
            request.setAttribute("textbook", textbook);
            request.getRequestDispatcher("/bookOrder.jsp").forward(request, response);
            return;
        }

        BookOrder order = new BookOrder();
        order.setUserId(user.getId());
        order.setTextbookId(textbookId);
        order.setQuantity(quantity);
        order.setRemark(remark);

        DataStore.addOrder(order);
        textbook.setStock(textbook.getStock() - quantity);

        request.setAttribute("success", "预订成功！");
        response.sendRedirect(request.getContextPath() + "/myOrder");
    }
}
