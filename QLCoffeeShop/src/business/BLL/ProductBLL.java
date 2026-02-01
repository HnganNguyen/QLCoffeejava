package business.BLL;

import data.DAL.ProductDAL;
import shared.DTO.ProductDTO;

import java.util.List;

public class ProductBLL {

    // =========================
    // LẤY SẢN PHẨM
    // =========================

    // Lấy sản phẩm theo loại + trạng thái
    public static List<ProductDTO> getSanPhamByIDLoaiSP(int idLoai, int status) {
        return ProductDAL.getSanPhamByIDLoaiSP(idLoai, status);
    }

    // Lấy sản phẩm theo mã
    public static List<ProductDTO> getListProductByID(int id) {
        return ProductDAL.getListProductByID(id);
    }

    // Kiểm tra mã loại sản phẩm đang được sử dụng
    public static int getMaLoaiBySP(int idLoai) {
        return ProductDAL.getMaLoaiBySP(idLoai);
    }

    // Lấy toàn bộ danh sách sản phẩm
    public static List<ProductDTO> getAllListProduct() {
        return ProductDAL.getAllListProduct();
    }

    // =========================
    // THÊM / SỬA / XÓA
    // =========================

    // Thêm sản phẩm mới
    public static boolean addProduct(ProductDTO product) {

        // if (product.getNameProducts().isEmpty()) return false;

        return ProductDAL.addProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    public static boolean updateProduct(ProductDTO product) {
        return ProductDAL.updateProduct(product);
    }

    // Xóa sản phẩm theo mã
    public static boolean deleteProduct(int id) {
        return ProductDAL.deleteProduct(id);
    }

    // Tìm kiếm sản phẩm theo tên
    public static List<ProductDTO> searchProductByName(String keyword) {
        return ProductDAL.searchProductByName(keyword);
    }
}