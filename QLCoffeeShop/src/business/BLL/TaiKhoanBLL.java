package business.BLL;

import java.util.List;
import data.DAL.TaiKhoanDAL;
import shared.DTO.TaiKhoanDTO;

public class TaiKhoanBLL {

    // 1️⃣ Lấy tất cả tài khoản
    public static List<TaiKhoanDTO> getAllTaiKhoan() {
        return TaiKhoanDAL.getAllTaiKhoan();
    }

    // 2️⃣ Thêm tài khoản
    public static boolean addTaiKhoan(TaiKhoanDTO tk) {
        return TaiKhoanDAL.addTaiKhoan(tk);
    }

    // 3️⃣ Cập nhật tài khoản
    public static boolean updateTaiKhoan(TaiKhoanDTO tk) {
        return TaiKhoanDAL.updateTaiKhoan(tk);
    }

    // 4️⃣ Xóa tài khoản
    public static boolean deleteTaiKhoan(int id) {
        return TaiKhoanDAL.deleteTaiKhoan(id);
    }

    // 5️⃣ Lấy danh sách theo trạng thái
    public static List<TaiKhoanDTO> getListAccountOnStatus(int status) {
        return TaiKhoanDAL.getListAccountOnStatus(status);
    }

    // 6️⃣ Đăng nhập (trả về đối tượng nếu đúng)
    public static TaiKhoanDTO dangNhap(int maTK, String password) {
        return TaiKhoanDAL.getAccountByUsernameAndPassword(maTK, password);
    }

    // 7️⃣ Tìm kiếm theo tên
    public static List<TaiKhoanDTO> searchTaiKhoanByName(String keyword) {
        return TaiKhoanDAL.searchTaiKhoanByName(keyword);
    }

    // 8️⃣ Lấy tài khoản theo ID
    public static TaiKhoanDTO getTaiKhoanById(int id) {
        return TaiKhoanDAL.getTaiKhoanById(id);
    }

    // 9️⃣ Lấy danh sách nhân viên (id + tên)
    public static List<TaiKhoanDTO> layDanhSachNhanVien() {
        return TaiKhoanDAL.layDanhSachNhanVien();
    }
}
