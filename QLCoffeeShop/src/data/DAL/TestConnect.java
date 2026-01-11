package data.DAL;
import shared.DTO.*;
import java.sql.Connection;

public class TestConnect {
    public static void main(String[] args) {
        Connection conn = MySQLConnect.getConnection();
        MySQLConnect.closeConnection(conn);
    }
}