package com.example.servlet;

import com.example.dao.DataStore;
import com.example.entity.Textbook;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "textbookListServlet", value = "/textbookList")
public class TextbookListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Textbook> textbooks = DataStore.getTextbooks();
        request.setAttribute("textbooks", textbooks);
        request.getRequestDispatcher("/textbookList.jsp").forward(request, response);
    }
}
