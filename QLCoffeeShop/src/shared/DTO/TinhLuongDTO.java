package shared.DTO;
import java.sql.Timestamp;

public class TinhLuongDTO {

    private int maLuong;
    private int thang;
    private int nam;
    private Timestamp ngayTao;
    private int ca;
    private double tong;
    private String ghiChu;
    private int tinhTrang;
    private String maTaiKhoan;

    public TinhLuongDTO() {}

    public TinhLuongDTO(int maLuong, int thang, int nam,
                        Timestamp ngayTao, int ca,
                        double tong, String ghiChu,
                        int tinhTrang, String maTaiKhoan) {
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

    public int getMaLuong() { return maLuong; }
    public void setMaLuong(int maLuong) { this.maLuong = maLuong; }

    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }

    public int getNam() { return nam; }
    public void setNam(int nam) { this.nam = nam; }

    public Timestamp getNgayTao() { return ngayTao; }
    public void setNgayTao(Timestamp ngayTao) { this.ngayTao = ngayTao; }

    public int getCa() { return ca; }
    public void setCa(int ca) { this.ca = ca; }

    public double getTong() { return tong; }
    public void setTong(double tong) { this.tong = tong; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public int getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(int tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getMaTaiKhoan() { return maTaiKhoan; }
    public void setMaTaiKhoan(String maTaiKhoan) { this.maTaiKhoan = maTaiKhoan; }
}