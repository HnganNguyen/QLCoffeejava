package data.DAL;

import shared.DTO.NguyenLieuDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NguyenLieuDAL {

    public static List<NguyenLieuDTO> getAllNguyenLieu() {
        List<NguyenLieuDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hangtonkho";

        try (Connection con = MySQLConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NguyenLieuDTO nl = new NguyenLieuDTO();
                nl.setMa(rs.getInt("MA"));
                nl.setTen(rs.getString("TEN"));
                nl.setNgayTao(rs.getTimestamp("NGAYTAO"));
                nl.setGiaGoc(rs.getDouble("GIAGOC"));
                nl.setGhiChu(rs.getString("GHICHU"));
                nl.setMaTaiKhoan(rs.getString("MATAIKHOAN"));
                list.add(nl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean insertNguyenLieu(NguyenLieuDTO nl) {
        String sql = """
            INSERT INTO hangtonkho (TEN, NGAYTAO, GIAGOC, GHICHU, MATAIKHOAN)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = MySQLConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nl.getTen());
            ps.setTimestamp(2, new Timestamp(nl.getNgayTao().getTime()));
            ps.setDouble(3, nl.getGiaGoc());
            ps.setString(4, nl.getGhiChu());
            ps.setString(5, nl.getMaTaiKhoan());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateNguyenLieu(NguyenLieuDTO nl) {
        String sql = """
            UPDATE hangtonkho
            SET TEN = ?, GIAGOC = ?, GHICHU = ?
            WHERE MA = ?
        """;

        try (Connection con = MySQLConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

    public static boolean deleteNguyenLieu(int ma) {
        String sql = "DELETE FROM hangtonkho WHERE MA = ?";

        try (Connection con = MySQLConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ma);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
