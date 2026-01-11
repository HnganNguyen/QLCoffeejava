package data.DAL;

import shared.DTO.BillDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAL {

    // 1️⃣ Lấy danh sách hóa đơn đã thanh toán (TRANGTHAI = 1)
    public static List<BillDTO> getAllListBill() {
        List<BillDTO> listBill = new ArrayList<>();
        String sql = "SELECT * FROM HOADON WHERE TRANGTHAI = 1";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listBill.add(new BillDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBill;
    }

    // 2️⃣ Lấy ID hóa đơn chưa thanh toán theo ID bàn
    public static int getIDBillNoPaymentByIDTable(int idTable) {
        String sql = "SELECT * FROM HOADON WHERE MABAN = ? AND TRANGTHAI = 0";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTable);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                BillDTO bill = new BillDTO(rs);
                return bill.getID();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 3️⃣ Lấy ID hóa đơn lớn nhất
    public static int getIDBillMax() {
        String sql = "SELECT MAX(MA) FROM HOADON";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next() && rs.getObject(1) != null) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    // 4️⃣ Cập nhật hóa đơn (thanh toán)
    public static void updateBill(int id,
                                  double totalBill,
                                  double promotion,
                                  double customerPrice,
                                  double outPrice,
                                  double revenue,
                                  Date dateTime,
                                  int employ) {

        String sql = "CALL USP_UpdateBill(?,?,?,?,?,?,?,?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setDouble(2, totalBill);
            ps.setTimestamp(3, new java.sql.Timestamp(dateTime.getTime()));
            ps.setInt(4, employ);
            ps.setDouble(5, promotion);
            ps.setDouble(6, customerPrice);
            ps.setDouble(7, outPrice);
            ps.setDouble(8, revenue);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 5️⃣ Thêm hóa đơn mới
    public static void insertBill(Date dateTime,
                                  double total,
                                  int employ,
                                  int idTable) {

        String sql = "CALL USP_InsertBILL(?,?,?,?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new java.sql.Timestamp(dateTime.getTime()));
            ps.setDouble(2, total);
            ps.setInt(3, employ);
            ps.setInt(4, idTable);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 6️⃣ Xóa hóa đơn
    public static void deleteBill(int idBill) {
        String sql = "DELETE FROM HOADON WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBill);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
