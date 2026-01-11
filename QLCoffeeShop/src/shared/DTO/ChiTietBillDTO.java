package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChiTietBillDTO {

    private int maBill;
    private int idProduct;
    private int soLuong;

    // 🔹 Constructor rỗng (bắt buộc cho DTO)
    public ChiTietBillDTO() {
    }

    // 🔹 Constructor đầy đủ tham số
    public ChiTietBillDTO(int maBill, int idProduct, int soLuong) {
        this.maBill = maBill;
        this.idProduct = idProduct;
        this.soLuong = soLuong;
    }

    // 🔹 Constructor từ ResultSet (tương đương DataRow bên C#)
    public ChiTietBillDTO(ResultSet rs) throws SQLException {
        this.maBill = rs.getInt("mahoadon");
        this.idProduct = rs.getInt("masanpham");
        this.soLuong = rs.getInt("soluong");
    }

    // ================= Getter & Setter =================

    public int getMaBill() {
        return maBill;
    }

    public void setMaBill(int maBill) {
        this.maBill = maBill;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
