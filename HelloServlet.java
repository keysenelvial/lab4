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

@WebServlet(name = "helloServlet", value = "/read")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;
    private Connection connection;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/read.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars", "root", "12345678");

            // Выполнение SQL-запроса для получения данных из базы данных
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars");

            // Создание списка автомобилей
            List<Car> carList = new ArrayList<>();

            // Итерация по результатам запроса и добавление объектов в список
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                String color = resultSet.getString("color");
                int year = resultSet.getInt("year");
                int mileage = resultSet.getInt("mileage");

                Car car = new Car(name, brand, color, year, mileage);
                carList.add(car);
            }

            // Преобразование списка автомобилей в JSON
            String json = new Gson().toJson(carList);

            // Отправка JSON-ответа клиенту
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

            // Закрытие соединения с базой данных
            resultSet.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Ошибка при подключении к базе данных");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
