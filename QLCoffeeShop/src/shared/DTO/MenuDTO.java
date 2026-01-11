package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuDTO {

    // ===== Fields =====
    private int id;          // MA (hóa đơn hoặc id dòng)
    private String nameProduct; // TENSANPHAM
    private int quantity;    // SOLUONG
    private double priceBasic; // GIACOBAN
    private double totalPrice; // TONGTIEN
    private int idProduct;   // MASANPHAM

    // ===== Constructor đầy đủ (tương đương constructor C# đầu tiên) =====
    public MenuDTO(int id, String nameProduct, int quantity,
                   double priceBasic, int idProduct, double totalPrice) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.priceBasic = priceBasic;
        this.totalPrice = totalPrice;
        this.idProduct = idProduct;
    }

    // ===== Constructor đọc từ ResultSet (tương đương DataRow) =====
    public MenuDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("MA");
        this.nameProduct = rs.getString("TENSANPHAM");
        this.quantity = rs.getInt("SOLUONG");
        this.priceBasic = rs.getDouble("GIACOBAN");
        this.totalPrice = rs.getDouble("TONGTIEN");
        this.idProduct = rs.getInt("MASANPHAM");
    }

    // ===== Getter & Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceBasic() {
        return priceBasic;
    }

    public void setPriceBasic(double priceBasic) {
        this.priceBasic = priceBasic;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
