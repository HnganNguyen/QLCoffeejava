package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDTO {

    private int id;
    private String nameProducts;
    private double priceBasic;
    private double salePrice;
    private int status;
    private int idTypeProduct;

    // Constructor rỗng
    public ProductDTO() {
    }

    // Constructor đầy đủ tham số
    public ProductDTO(int id,
                      String nameProducts,
                      double priceBasic,
                      double salePrice,
                      int status,
                      int idTypeProduct) {
        this.id = id;
        this.nameProducts = nameProducts;
        this.priceBasic = priceBasic;
        this.salePrice = salePrice;
        this.status = status;
        this.idTypeProduct = idTypeProduct;
    }

    // Constructor từ ResultSet (tương đương DataRow trong C#)
    public ProductDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("ma");
        this.nameProducts = rs.getString("tensanpham");
        this.priceBasic = rs.getDouble("giacoban");

        // Kiểm tra NULL giống C#
        this.salePrice = rs.getObject("giakhuyenmai") != null
                ? rs.getDouble("giakhuyenmai")
                : 0;

        this.status = rs.getInt("trangthai");
        this.idTypeProduct = rs.getInt("maloaisanpham");
    }

    // ===== Getter & Setter =====

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNameProducts() {
        return nameProducts;
    }

    public void setNameProducts(String nameProducts) {
        this.nameProducts = nameProducts;
    }

    public double getPriceBasic() {
        return priceBasic;
    }

    public void setPriceBasic(double priceBasic) {
        this.priceBasic = priceBasic;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIDTypeProduct() {
        return idTypeProduct;
    }

    public void setIDTypeProduct(int idTypeProduct) {
        this.idTypeProduct = idTypeProduct;
    }
}
