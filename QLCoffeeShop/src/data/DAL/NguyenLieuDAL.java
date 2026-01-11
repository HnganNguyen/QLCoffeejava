package data.DAL;

import shared.DTO.NguyenLieuDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDAL {

    // 1️⃣ Lấy tất cả nguyên liệu
    public static List<NguyenLieuDTO> getAllNguyenLieu() {
        List<NguyenLieuDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM HANGTONKHO";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NguyenLieuDTO nl = new NguyenLieuDTO(
                        rs.getInt("MA"),
                        rs.getString("TEN"),
                        rs.getTimestamp("NGAYTAO"),
                        rs.getDouble("GIAGOC"),
                        rs.getString("GHICHU") != null ? rs.getString("GHICHU") : "",
                        rs.getInt("MATAIKHOAN")
                );
                list.add(nl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Thêm nguyên liệu
    public static boolean insertNguyenLieu(NguyenLieuDTO nl) {

        String sql = """
            INSERT INTO HANGTONKHO
            (TEN, NGAYTAO, GIAGOC, GHICHU, MATAIKHOAN)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nl.getTen());
            ps.setTimestamp(2, new Timestamp(nl.getNgayTao().getTime()));
            ps.setDouble(3, nl.getGiaGoc());

            if (nl.getGhiChu() == null || nl.getGhiChu().isEmpty()) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, nl.getGhiChu());
            }

            ps.setInt(5, nl.getMaTaiKhoan());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3️⃣ Cập nhật nguyên liệu
    public static boolean updateNguyenLieu(NguyenLieuDTO nl) {

        String sql = """
            UPDATE HANGTONKHO
            SET TEN = ?,
                GIAGOC = ?,
                GHICHU = ?
            WHERE MA = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nl.getTen());
            ps.setDouble(2, nl.getGiaGoc());
            ps.setString(3, nl.getGhiChu());
            ps.setInt(4, nl.getMa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean EditNguyenLieu(NguyenLieuDTO nl) {
        String sql = "UPDATE HANGTONKHO SET TEN = ?, GIAGOC = ?, GHICHU = ? WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nl.getTen());
            ps.setDouble(2, nl.getGiaGoc());
            ps.setString(3, nl.getGhiChu());
            ps.setInt(4, nl.getMa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    
}


    // 4️⃣ Xóa nguyên liệu
    public static void deleteNguyenLieu(int ma) {

        String sql = "DELETE FROM HANGTONKHO WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ma);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
