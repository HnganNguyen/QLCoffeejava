package data.DAL;

import shared.DTO.MenuDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDAL {

    // 1️⃣ Danh sách menu theo ID bàn (chưa thanh toán)
    public static List<MenuDTO> getListMenuByIDTable(int idTable) {
        List<MenuDTO> listMenu = new ArrayList<>();

        String sql = """
            SELECT DE.MASANPHAM,
                   D.MA,
                   D.TENSANPHAM,
                   DE.SOLUONG,
                   D.GIACOBAN,
                   DE.SOLUONG * D.GIACOBAN AS TONGTIEN,
                   BI.TRANGTHAI
            FROM HOADON BI
            JOIN CHITIETHOADON DE ON DE.MAHOADON = BI.MA
            JOIN SANPHAM D ON DE.MASANPHAM = D.MA
            WHERE BI.TRANGTHAI = 0 AND BI.MABAN = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTable);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listMenu.add(new MenuDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    // 2️⃣ Danh sách menu theo ID hóa đơn (chưa thanh toán)
    public static List<MenuDTO> getListMenuByIDBill(int idBill) {
        List<MenuDTO> listMenu = new ArrayList<>();

        String sql = """
            SELECT DE.MASANPHAM,
                   D.MA,
                   D.TENSANPHAM,
                   DE.SOLUONG,
                   D.GIACOBAN,
                   DE.SOLUONG * D.GIACOBAN AS TONGTIEN,
                   BI.TRANGTHAI
            FROM HOADON BI
            JOIN CHITIETHOADON DE ON DE.MAHOADON = BI.MA
            JOIN SANPHAM D ON DE.MASANPHAM = D.MA
            WHERE BI.TRANGTHAI = 0 AND DE.MAHOADON = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBill);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listMenu.add(new MenuDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    // 3️⃣ Xem lại hóa đơn đã thanh toán
    public static List<MenuDTO> getReviewBill(int idBill) {
        List<MenuDTO> listMenu = new ArrayList<>();

        String sql = """
            SELECT DE.MASANPHAM,
                   D.MA,
                   D.TENSANPHAM,
                   DE.SOLUONG,
                   D.GIACOBAN,
                   DE.SOLUONG * D.GIACOBAN AS TONGTIEN,
                   BI.TRANGTHAI
            FROM HOADON BI
            JOIN CHITIETHOADON DE ON DE.MAHOADON = BI.MA
            JOIN SANPHAM D ON DE.MASANPHAM = D.MA
            WHERE BI.TRANGTHAI = 1 AND DE.MAHOADON = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBill);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listMenu.add(new MenuDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMenu;
    }
}
