package business.BLL;

import data.DAL.*;
import shared.DTO.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TinhLuongBLL {

	private TinhLuongDAL tinhLuongDAL = new TinhLuongDAL();

    // Lấy tất cả lương
    public List<TinhLuongDTO> getAllTinhLuong() {
        return tinhLuongDAL.getAllTinhLuong();
    }

    // Thêm lương
    public boolean themLuong(TinhLuongDTO tinhLuong) {
        return tinhLuongDAL.insertTinhLuong(tinhLuong);
    }

    // Thanh toán lương
    public boolean thanhToanLuong(String maTaiKhoan, int thang, int nam) {
        return tinhLuongDAL.thanhToanLuong(maTaiKhoan, thang, nam);
    }

    // Lấy tên nhân viên
    public String getTenNhanVien(String maTaiKhoan) {
        return tinhLuongDAL.layTenNhanVien(maTaiKhoan);
    }

    // Lấy số điện thoại
    public String getSDTNhanVien(String maTaiKhoan) {
        return tinhLuongDAL.laySDTNhanVien(maTaiKhoan);
    }

    // Lấy địa chỉ
    public String getDiaChiNhanVien(String maTaiKhoan) {
        return tinhLuongDAL.layDiaChiNhanVien(maTaiKhoan);
    }

    // Lấy lương cơ bản
    public float getLuongCoBan(String maTaiKhoan) {
        return tinhLuongDAL.layLuongCoBan(maTaiKhoan);
    }
    public List<TaiKhoanDTO> getAllNhanVien() {
        return tinhLuongDAL.getAllNhanVien();
    }
}