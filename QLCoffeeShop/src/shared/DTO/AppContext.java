package shared.DTO;



import shared.DTO.TaiKhoanDTO;

public class AppContext {

    // 🔐 Tài khoản đang đăng nhập
    public static TaiKhoanDTO taiKhoanDangNhap = null;

    // Kiểm tra đã login chưa
    public static boolean isLogin() {
        return taiKhoanDangNhap != null;
    }

    // Logout
    public static void clear() {
        taiKhoanDangNhap = null;
    }
}
