package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDTO {

    private int id;
    private String password;
    private String tenTK;
    private String cccd;
    private String sdt;
    private String diaChi;
    private int quyen;
    private int trangThai;
    private double luongByCa;

    // ✅ Constructor rỗng
    public TaiKhoanDTO() {
    }

    // ✅ Constructor từ ResultSet (tương đương DataRow bên C#)
    public TaiKhoanDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("MATAIKHOAN");
        this.password = rs.getString("PASS");
        this.tenTK = rs.getString("TEN");
        this.cccd = rs.getString("CCCD");
        this.sdt = rs.getString("SODIENTHOAI");
        this.diaChi = rs.getString("DIACHI");
        this.quyen = rs.getInt("QUYEN");
        this.trangThai = rs.getInt("TRANGTHAI");
        this.luongByCa = rs.getDouble("LUONG");
    }

    // ✅ Constructor đầy đủ tham số
    public TaiKhoanDTO(int id, String tenTK, String password, String cccd,
                       String sdt, String diaChi, int quyen,
                       int trangThai, double luongByCa) {
        this.id = id;
        this.tenTK = tenTK;
        this.password = password;
        this.cccd = cccd;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.quyen = quyen;
        this.trangThai = trangThai;
        this.luongByCa = luongByCa;
    }

    // ================== Getter & Setter ==================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getQuyen() {
        return quyen;
    }

    public void setQuyen(int quyen) {
        this.quyen = quyen;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getLuongByCa() {
        return luongByCa;
    }

    public void setLuongByCa(double luongByCa) {
        this.luongByCa = luongByCa;
    }
}
