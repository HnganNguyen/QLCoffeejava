package shared.DTO;

import java.util.Date;

public class TinhLuongDTO {

    private int maLuong;
    private int thang;
    private int nam;
    private Date ngayTao;
    private int ca;
    private float tong;
    private String ghiChu;
    private int tinhTrang;
    private int maTaiKhoan;

    // Constructor không tham số
    public TinhLuongDTO() {
    }

    // Constructor đầy đủ tham số
    public TinhLuongDTO(int maLuong, int thang, int nam, Date ngayTao,
                        int ca, float tong, String ghiChu,
                        int tinhTrang, int maTaiKhoan) {
        this.maLuong = maLuong;
        this.thang = thang;
        this.nam = nam;
        this.ngayTao = ngayTao;
        this.ca = ca;
        this.tong = tong;
        this.ghiChu = ghiChu;
        this.tinhTrang = tinhTrang;
        this.maTaiKhoan = maTaiKhoan;
    }

    // Getter & Setter
    public int getMaLuong() {
        return maLuong;
    }

    public void setMaLuong(int maLuong) {
        this.maLuong = maLuong;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public float getTong() {
        return tong;
    }

    public void setTong(float tong) {
        this.tong = tong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
}
