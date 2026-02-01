package data.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shared.DTO.TaiKhoanDTO;

public class TaiKhoanDAL {

    // 1️⃣ Lấy tất cả tài khoản
    public static List<TaiKhoanDTO> getAllTaiKhoan() {
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

    // 2️⃣ Thêm tài khoản
    public static boolean addTaiKhoan(TaiKhoanDTO tk) {
        String sql = """
            INSERT INTO TAIKHOAN
            (MATAIKHOAN, TEN, PASS, CCCD, SODIENTHOAI, DIACHI, QUYEN, TRANGTHAI, LUONG)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getId());
            ps.setString(2, tk.getTenTK());
            ps.setString(3, tk.getPassword());
            ps.setString(4, tk.getCccd());
            ps.setString(5, tk.getSdt());
            ps.setString(6, tk.getDiaChi());
            ps.setInt(7, tk.getQuyen());
            ps.setInt(8, tk.getTrangThai());
            ps.setDouble(9, tk.getLuongByCa());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3️⃣ Cập nhật tài khoản
    public static boolean updateTaiKhoan(TaiKhoanDTO tk) {
        String sql = """
            UPDATE TAIKHOAN SET
            TEN = ?, PASS = ?, CCCD = ?, SODIENTHOAI = ?,
            DIACHI = ?, QUYEN = ?, TRANGTHAI = ?, LUONG = ?
            WHERE MATAIKHOAN = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getTenTK());
            ps.setString(2, tk.getPassword());
            ps.setString(3, tk.getCccd());
            ps.setString(4, tk.getSdt());
            ps.setString(5, tk.getDiaChi());
            ps.setInt(6, tk.getQuyen());
            ps.setInt(7, tk.getTrangThai());
            ps.setDouble(8, tk.getLuongByCa());
            ps.setString(9, tk.getId());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4️⃣ Xóa tài khoản
    public static boolean deleteTaiKhoan(String id) {
        String sql = "DELETE FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5️⃣ Lấy danh sách theo trạng thái
    public static List<TaiKhoanDTO> getListAccountOnStatus(int status) {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN WHERE QUYEN IN (0,1) AND TRANGTHAI = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TaiKhoanDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ❌ CŨ – giữ nếu cần
    public static boolean dangNhap(String maTK, String password) {
        String sql = "SELECT 1 FROM TAIKHOAN WHERE MATAIKHOAN = ? AND PASS = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTK);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ LOGIN CHUẨN
    public static TaiKhoanDTO login(String maTK, String password) {
        String sql = """
            SELECT * FROM TAIKHOAN
            WHERE MATAIKHOAN = ?
              AND PASS = ?
              AND TRANGTHAI = 1
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTK);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new TaiKhoanDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 8️⃣ Tìm theo tên
    public static List<TaiKhoanDTO> searchTaiKhoanByName(String keyword) {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM TAIKHOAN WHERE TEN LIKE ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new TaiKhoanDTO(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 9️⃣ Lấy theo ID
    public static TaiKhoanDTO getTaiKhoanById(String id) {
        String sql = "SELECT * FROM TAIKHOAN WHERE MATAIKHOAN = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new TaiKhoanDTO(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔟 Danh sách nhân viên
    public static List<TaiKhoanDTO> layDanhSachNhanVien() {
        List<TaiKhoanDTO> list = new ArrayList<>();
        String sql = "SELECT MATAIKHOAN, TEN FROM TAIKHOAN";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TaiKhoanDTO tk = new TaiKhoanDTO();
                tk.setId(rs.getString("MATAIKHOAN"));
                tk.setTenTK(rs.getString("TEN"));
                list.add(tk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
 // 🔟 Đổi mật khẩu (dùng cho ChangePasswordGUI)
    public static boolean changePassword(String maTK, String oldPass, String newPass) {

        String sql = """
            UPDATE TAIKHOAN
            SET PASS = ?
            WHERE MATAIKHOAN = ?
              AND PASS = ?
              AND TRANGTHAI = 1
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPass);
            ps.setString(2, maTK);
            ps.setString(3, oldPass);

            // nếu update được 1 dòng => mật khẩu cũ đúng
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String getMaTaiKhoanByUsername(String username) {
        String sql = "SELECT MATAIKHOAN FROM taikhoan WHERE TEN=?";
        try (Connection con = MySQLConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("MATAIKHOAN");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
