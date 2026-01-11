package business.BLL;

import data.DAL.NguyenLieuDAL;
import shared.DTO.NguyenLieuDTO;

import java.util.List;

public class NguyenLieuBLL {

    public static List<NguyenLieuDTO> getAllNguyenLieu() {
        return NguyenLieuDAL.getAllNguyenLieu();
    }

    public static boolean insertNguyenLieu(NguyenLieuDTO nl) {
        return NguyenLieuDAL.insertNguyenLieu(nl);
    }

    public static List<NguyenLieuDTO> updateNguyenLieu(NguyenLieuDTO nl) {
        NguyenLieuDAL.updateNguyenLieu(nl);
        return getAllNguyenLieu(); // Trả về danh sách mới sau khi cập nhật
    }

    public static boolean EditNguyenLieu(NguyenLieuDTO nl) {
        return NguyenLieuDAL.EditNguyenLieu(nl);
    }

    public static List<NguyenLieuDTO> deleteNguyenLieu(int ma) {
        NguyenLieuDAL.deleteNguyenLieu(ma);
        return getAllNguyenLieu(); // Trả về danh sách mới sau khi xóa
    }
}
