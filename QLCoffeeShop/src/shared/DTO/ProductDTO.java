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
    private String img;

    // Constructor rỗng
    public ProductDTO() {
    }

    public ProductDTO(int id,
                      String nameProducts,
                      double priceBasic,
                      double salePrice,
                      int status,
                      int idTypeProduct,
                      String img) {
        this.id = id;
        this.nameProducts = nameProducts;
        this.priceBasic = priceBasic;
        this.salePrice = salePrice;
        this.status = status;
        this.idTypeProduct = idTypeProduct;
        this.img = img;
    }

    public ProductDTO(int id,
                      String nameProducts,
                      double priceBasic,
                      double salePrice,
                      int status,
                      int idTypeProduct) {
        this(id, nameProducts, priceBasic, salePrice, status, idTypeProduct, null);
    }

    // Constructor từ ResultSet
    public ProductDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("ma");
        this.nameProducts = rs.getString("tensanpham");
        this.priceBasic = rs.getDouble("giacoban");

        this.salePrice = rs.getObject("giakhuyenmai") != null
                ? rs.getDouble("giakhuyenmai")
                : 0;

        this.status = rs.getInt("trangthai");
        this.idTypeProduct = rs.getInt("maloaisanpham");

        this.img = rs.getString("img");
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
