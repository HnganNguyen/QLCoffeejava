package business.BLL;

import data.DAL.NguyenLieuDAL;
import shared.DTO.NguyenLieuDTO;

import java.util.List;

public class NguyenLieuBLL {

    // Lấy danh sách nguyên liệu
    public static List<NguyenLieuDTO> getAll() {
        return NguyenLieuDAL.getAllNguyenLieu();
    }

    // Thêm nguyên liệu
    public static boolean insert(NguyenLieuDTO nl) {
        return NguyenLieuDAL.insertNguyenLieu(nl);
    }

    // CẬP NHẬT NGUYÊN LIỆU  ❗ SỬA Ở ĐÂY
    public static boolean update(NguyenLieuDTO nl) {
        return NguyenLieuDAL.updateNguyenLieu(nl); // ✅ ĐÚNG TÊN HÀM DAL
    }

    // Xoá nguyên liệu
    public static boolean delete(int ma) {
        return NguyenLieuDAL.deleteNguyenLieu(ma);
    }
}
