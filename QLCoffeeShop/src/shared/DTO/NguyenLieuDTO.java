package shared.DTO;

import java.util.Date;

public class NguyenLieuDTO {

    private int ma;
    private String ten;
    private Date ngayTao;
    private double giaGoc;
    private String ghiChu;
    private String maTaiKhoan; // varchar(20)

    public NguyenLieuDTO() {
    }

    public NguyenLieuDTO(int ma, String ten, Date ngayTao,
                         double giaGoc, String ghiChu, String maTaiKhoan) {
        this.ma = ma;
        this.ten = ten;
        this.ngayTao = ngayTao;
        this.giaGoc = giaGoc;
        this.ghiChu = ghiChu;
        this.maTaiKhoan = maTaiKhoan;
    }

    // ===== GETTER =====
    public int getMa() {
        return ma;
    }

    public String getTen() {
        return ten;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    // ===== SETTER =====
    public void setMa(int ma) {
        this.ma = ma;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
}
