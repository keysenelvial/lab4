package com.example.lab1oop;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "addServlet", value = "/add")
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Перенаправление запроса к файлу add.jsp
        request.getRequestDispatcher("/pages/add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение данных из запроса
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String color = request.getParameter("color");
        int year = Integer.parseInt(request.getParameter("year"));
        int mileage = Integer.parseInt(request.getParameter("mileage"));

        // Создание нового объекта автомобиля
        Car car = new Car(name, brand, color, year, mileage);

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Установка соединения с базой данных
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "12345678");

            // Создание подготовленного SQL-запроса для добавления данных в таблицу
            String query = "INSERT INTO cars (name, brand, color, year, mileage) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setString(2, car.getBrand());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.setInt(4, car.getYear());
            preparedStatement.setInt(5, car.getMileage());

            // Выполнение SQL-запроса для добавления данных в базу данных
            preparedStatement.executeUpdate();

            // Отправка успешного ответа клиенту
            response.setStatus(HttpServletResponse.SC_OK);

            // Перенаправление пользователя на другую страницу
            response.sendRedirect("/pages/success.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок соединения с базой данных
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при подключении к базе данных");
        } finally {
            // Закрытие ресурсов
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
