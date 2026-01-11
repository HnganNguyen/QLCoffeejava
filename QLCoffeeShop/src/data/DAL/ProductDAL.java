package data.DAL;

import shared.DTO.ProductDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAL {

    // 1️⃣ Lấy sản phẩm theo ID loại + trạng thái
    public static List<ProductDTO> getSanPhamByIDLoaiSP(int idLoai, int status) {
        List<ProductDTO> listProduct = new ArrayList<>();
        String sql;

        if (idLoai == 0) {
            sql = """
                SELECT *
                FROM SANPHAM
                WHERE TRANGTHAI = ?
                AND MALOAISANPHAM NOT IN (
                    SELECT MA FROM LOAISANPHAM WHERE TRANGTHAI = 0
                )
                LIMIT 10
            """;
        } else {
            if (status == -1) {
                sql = "SELECT * FROM SANPHAM WHERE MALOAISANPHAM = ?";
            } else {
                sql = "SELECT * FROM SANPHAM WHERE MALOAISANPHAM = ? AND TRANGTHAI = ?";
            }
        }

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (idLoai == 0) {
                ps.setInt(1, status);
            } else {
                ps.setInt(1, idLoai);
                if (status != -1) {
                    ps.setInt(2, status);
                }
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listProduct.add(new ProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProduct;
    }

    // 2️⃣ Lấy mã loại sản phẩm từ sản phẩm
    public static int getMaLoaiBySanPham(int idLoai) {

        String sql = "SELECT MALOAISANPHAM FROM SANPHAM WHERE MALOAISANPHAM = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLoai);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("MALOAISANPHAM");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 3️⃣ Lấy tất cả sản phẩm
    public static List<ProductDTO> getAllListProduct() {
        List<ProductDTO> listProduct = new ArrayList<>();

        String sql = "SELECT * FROM SANPHAM";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                listProduct.add(new ProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProduct;
    }

    // 4️⃣ Lấy sản phẩm theo ID
    public static List<ProductDTO> getListProductByID(int id) {
        List<ProductDTO> listProduct = new ArrayList<>();

        String sql = "SELECT * FROM SANPHAM WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listProduct.add(new ProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProduct;
    }

    // 5️⃣ Thêm sản phẩm
    public static boolean addProduct(ProductDTO product) {

        String sql = """
            INSERT INTO SANPHAM
            (TENSANPHAM, GIACOBAN, GIAKHUYENMAI, MALOAISANPHAM, TRANGTHAI)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getNameProducts());
            ps.setDouble(2, product.getPriceBasic());
            ps.setDouble(3, product.getSalePrice());
            ps.setInt(4, product.getIDTypeProduct());
            ps.setInt(5, product.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6️⃣ Cập nhật sản phẩm
    public static boolean updateProduct(ProductDTO product) {

        String sql = """
            UPDATE SANPHAM
            SET TENSANPHAM = ?,
                GIACOBAN = ?,
                GIAKHUYENMAI = ?,
                TRANGTHAI = ?,
                MALOAISANPHAM = ?
            WHERE MA = ?
        """;

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getNameProducts());
            ps.setDouble(2, product.getPriceBasic());
            ps.setDouble(3, product.getSalePrice());
            ps.setInt(4, product.getStatus());
            ps.setInt(5, product.getIDTypeProduct());
            ps.setInt(6, product.getID());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7️⃣ Xóa sản phẩm
    public static boolean deleteProduct(int id) {

        String sql = "DELETE FROM SANPHAM WHERE MA = ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 8️⃣ Tìm kiếm sản phẩm theo tên
    public static List<ProductDTO> searchProductByName(String keyword) {
        List<ProductDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM SANPHAM WHERE TENSANPHAM LIKE ?";

        try (Connection conn = MySQLConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ProductDTO(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
