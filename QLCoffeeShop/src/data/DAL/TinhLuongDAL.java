package data.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import shared.DTO.*;
import java.util.ArrayList;
import java.util.List;

public class TinhLuongDAL {

    // ================== LẤY TẤT CẢ LƯƠNG ==================
    public List<TinhLuongDTO> getAllTinhLuong() {
        List<TinhLuongDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TINHLUONGNHANVIEN";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TinhLuongDTO tl = new TinhLuongDTO(
                        rs.getInt("MALUONG"),
                        rs.getInt("THANG"),
                        rs.getInt("NAM"),
                        rs.getTimestamp("NGAYTAO"),
                        rs.getInt("CA"),
                        rs.getDouble("TONG"),
                        rs.getString("GHICHU"),
                        rs.getInt("TINHTRANG"),
                        rs.getString("MATAIKHOAN")
                );
                list.add(tl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static List<TaiKhoanDTO> getAllNhanVien() {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new TaiKhoanDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // ================== INSERT ==================
    public boolean insertTinhLuong(TinhLuongDTO tl) {
        String sql = "INSERT INTO TINHLUONGNHANVIEN " +
                     "(THANG, NAM, NGAYTAO, CA, TONG, GHICHU, TINHTRANG, MATAIKHOAN) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tl.getThang());
            ps.setInt(2, tl.getNam());
            ps.setTimestamp(3, tl.getNgayTao());
            ps.setInt(4, tl.getCa());
            ps.setDouble(5, tl.getTong());
            ps.setString(6, tl.getGhiChu());
            ps.setInt(7, tl.getTinhTrang());
            ps.setString(8, tl.getMaTaiKhoan());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== THANH TOÁN ==================
    public boolean thanhToanLuong(String maTaiKhoan, int thang, int nam) {
        String sql = "UPDATE TINHLUONGNHANVIEN SET TINHTRANG = 1 " +
                     "WHERE MATAIKHOAN = ? AND THANG = ? AND NAM = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ps.setInt(2, thang);
            ps.setInt(3, nam);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================== LẤY TÊN NHÂN VIÊN ==================
    public String layTenNhanVien(String maTaiKhoan) {
        String sql = "SELECT TEN FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("TEN");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ================== LẤY SĐT ==================
    public String laySDTNhanVien(String maTaiKhoan) {
        String sql = "SELECT SODIENTHOAI FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("SODIENTHOAI");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ================== LẤY ĐỊA CHỈ ==================
    public String layDiaChiNhanVien(String maTaiKhoan) {
        String sql = "SELECT DIACHI FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("DIACHI");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ================== LẤY LƯƠNG CƠ BẢN ==================
    public float layLuongCoBan(String maTaiKhoan) {
        String sql = "SELECT LUONG FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiKhoan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getFloat("LUONG");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}