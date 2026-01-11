package business.BLL;

import data.DAL.ProductDAL;
import shared.DTO.ProductDTO;

import java.util.List;

public class ProductBLL {

    public static List<ProductDTO> getSanPhamByIDLoaiSP(int id, int status) {
        return ProductDAL.getSanPhamByIDLoaiSP(id, status);
    }

    public static List<ProductDTO> getListProductByID(int id) {
        return ProductDAL.getListProductByID(id);
    }

    public static int getMaLoaiBySP(int id) {
        return ProductDAL.getMaLoaiBySP(id);
    }

    public static List<ProductDTO> getAllListProduct() {
        return ProductDAL.getAllListProduct();
    }

    // thêm vô
    public static boolean addProduct(ProductDTO product) {
        return ProductDAL.addProduct(product);
    }

    public static boolean updateProduct(ProductDTO product) {
        return ProductDAL.updateProduct(product);
    }

    public static boolean deleteProduct(int id) {
        return ProductDAL.deleteProduct(id);
    }

    // tìm
    public static List<ProductDTO> searchProductByName(String keyword) {
        return ProductDAL.searchProductByName(keyword);
    }
}
