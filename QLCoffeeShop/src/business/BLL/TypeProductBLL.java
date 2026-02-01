package business.BLL;

import java.util.List;
import data.DAL.TypeProductDAL;
import shared.DTO.TypeProductDTO;

public class TypeProductBLL {

    // 🔹 Lấy tất cả loại sản phẩm
    public static List<TypeProductDTO> getAllListTypeProduct() {
        return TypeProductDAL.getAllListTypeProduct();
    }

    // 🔹 Lấy danh sách loại sản phẩm theo trạng thái


        public static List<TypeProductDTO> getListTypeProductByStatus(int status) {
            return TypeProductDAL.getListTypeProductByStatus(status);
        }
    


    // 🔹 Thêm loại sản phẩm
    public static boolean insertTypeProduct(TypeProductDTO typeProduct) {
        return TypeProductDAL.insertTypeProduct(typeProduct);
    }

    // 🔹 Cập nhật loại sản phẩm
    public static boolean updateTypeProduct(TypeProductDTO typeProduct) {
        return TypeProductDAL.updateTypeProduct(typeProduct);
    }

    // 🔹 Xóa loại sản phẩm
    public static boolean deleteTypeProduct(TypeProductDTO typeProduct) {
        return TypeProductDAL.deleteTypeProduct(typeProduct);
    }

    // 🔹 Lấy tên loại sản phẩm theo ID
    public static String getTypeNameByID(int id) {
        return TypeProductDAL.getTypeNameByID(id);
    }

    // 🔹 Lấy ID theo tên loại sản phẩm
    public static int getIDByTypeName(String name) {
        return TypeProductDAL.getIDByTypeName(name);
    }
}
