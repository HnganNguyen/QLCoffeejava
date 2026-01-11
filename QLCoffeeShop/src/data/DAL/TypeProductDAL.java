package data.DAL;

import shared.DTO.TypeProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TypeProductDAL {

    // 1️⃣ Lấy danh sách loại sản phẩm theo trạng thái (0/1)
    public static List<TypeProductDTO> getListTypeProductWithStatus(int status) {
        List<TypeProductDTO> listType = new ArrayList<>();

        String sql = "SELECT * FROM LOAISANPHAM WHERE TRANGTHAI = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listType.add(new TypeProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listType;
    }

    // 2️⃣ Lấy toàn bộ loại sản phẩm
    public static List<TypeProductDTO> getAllListTypeProduct() {
        List<TypeProductDTO> listType = new ArrayList<>();

        String sql = "SELECT * FROM LOAISANPHAM";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listType.add(new TypeProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listType;
    }

    // 3️⃣ Thêm loại sản phẩm (Stored Procedure)
    public static boolean insertTypeProduct(TypeProductDTO typeProduct) {

        String sql = "CALL USP_INSERTLOAISANPHAM(?, ?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, typeProduct.getNameType());
            ps.setInt(2, typeProduct.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4️⃣ Cập nhật loại sản phẩm
    public static boolean updateTypeProduct(TypeProductDTO typeProduct) {

        String sql = "CALL USP_UPDATELOAISANPHAM(?, ?, ?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeProduct.getId());
            ps.setString(2, typeProduct.getNameType());
            ps.setInt(3, typeProduct.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 5️⃣ Xóa loại sản phẩm
    public static boolean deleteTypeProduct(TypeProductDTO typeProduct) {

        String sql = "CALL USP_DELETELOAISANPHAM(?)";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeProduct.getId());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6️⃣ Lấy tên loại theo ID
    public static String getTypeNameByID(int id) {

        String sql = "SELECT TENLOAI FROM LOAISANPHAM WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("TENLOAI");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 7️⃣ Lấy ID theo tên loại
    public static int getIDByTypeName(String name) {

        String sql = "SELECT MA FROM LOAISANPHAM WHERE TENLOAI = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("MA");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}

