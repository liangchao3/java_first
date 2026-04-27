package com.example.dao;

import com.example.entity.BookOrder;
import com.example.entity.Textbook;
import com.example.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DataStore {
    private static final List<User> users = new ArrayList<>();
    private static final List<Textbook> textbooks = new ArrayList<>();
    private static final List<BookOrder> orders = new ArrayList<>();

    private static final AtomicInteger userIdGenerator = new AtomicInteger(1000);
    private static final AtomicInteger textbookIdGenerator = new AtomicInteger(100);
    private static final AtomicInteger orderIdGenerator = new AtomicInteger(1);

    static {
        // 初始化用户数据
        users.add(new User(userIdGenerator.getAndIncrement(), "admin", "admin", "管理员", "admin"));
        users.add(new User(userIdGenerator.getAndIncrement(), "zhangsan", "123456", "张三", "student"));
        users.add(new User(userIdGenerator.getAndIncrement(), "lisi", "123456", "李四", "student"));
        users.add(new User(userIdGenerator.getAndIncrement(), "wangwu", "123456", "王五", "student"));

        // 初始化教材数据
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "Java程序设计", "张三", "清华大学出版社", "9787302423285", 45.00, 100, "计算机基础"));
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "数据结构", "李四", "人民邮电出版社", "9787115423370", 39.50, 80, "计算机基础"));
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "操作系统原理", "王五", "高等教育出版社", "9787040403877", 52.00, 60, "计算机核心"));
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "计算机网络", "赵六", "电子工业出版社", "9787121258904", 48.00, 75, "计算机核心"));
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "数据库系统概论", "孙七", "机械工业出版社", "9787111572874", 42.00, 90, "计算机核心"));
        textbooks.add(new Textbook(textbookIdGenerator.getAndIncrement(), "Web前端开发技术", "周八", "清华大学出版社", "9787302487102", 36.00, 120, "Web开发"));
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Textbook> getTextbooks() {
        return textbooks;
    }

    public static List<BookOrder> getOrders() {
        return orders;
    }

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static Textbook findTextbookById(int id) {
        for (Textbook textbook : textbooks) {
            if (textbook.getId() == id) {
                return textbook;
            }
        }
        return null;
    }

    public static BookOrder findOrderById(int id) {
        for (BookOrder order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public static List<BookOrder> findOrdersByUserId(int userId) {
        List<BookOrder> result = new ArrayList<>();
        for (BookOrder order : orders) {
            if (order.getUserId() == userId) {
                result.add(order);
            }
        }
        return result;
    }

    public static void addOrder(BookOrder order) {
        order.setId(orderIdGenerator.getAndIncrement());
        orders.add(order);
    }

    public static boolean updateOrder(BookOrder order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == order.getId()) {
                orders.set(i, order);
                return true;
            }
        }
        return false;
    }

    public static boolean cancelOrder(int orderId) {
        BookOrder order = findOrderById(orderId);
        if (order != null && !"cancelled".equals(order.getStatus())) {
            order.setStatus("cancelled");
            return true;
        }
        return false;
    }

    public static int generateOrderId() {
        return orderIdGenerator.getAndIncrement();
    }
}
