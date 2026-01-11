package data.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnect {
	   
    // THÔNG TIN KẾT NỐI - ĐIỀU CHỈNH THEO CỦA BẠN
    private static final String DB_URL = "jdbc:mysql://localhost:3306/qlcoffeeshop";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = ""; // 
 // HÀM KẾT NỐI
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // MySQL Connector/J 8+
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("✔ Kết nối MySQL thành công!");

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy MySQL Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Kết nối MySQL thất bại");
            e.printStackTrace();
        }
        return conn;
    }

    // ĐÓNG KẾT NỐI
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
