package shared.DTO;

import java.util.Date;

public class NguyenLieuDTO {

    private int ma;
    private String ten;
    private Date ngayTao;
    private double giaGoc;
    private String ghiChu;
    private int maTaiKhoan;

    // Constructor rỗng
    public NguyenLieuDTO() {
    }

    // Constructor đầy đủ tham số
    public NguyenLieuDTO(int ma,
                         String ten,
                         Date ngayTao,
                         double giaGoc,
                         String ghiChu,
                         int maTaiKhoan) {
        this.ma = ma;
        this.ten = ten;
        this.ngayTao = ngayTao;
        this.giaGoc = giaGoc;
        this.ghiChu = ghiChu;
        this.maTaiKhoan = maTaiKhoan;
    }

    // ===== Getter & Setter =====

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(double giaGoc) {
        this.giaGoc = giaGoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
}
