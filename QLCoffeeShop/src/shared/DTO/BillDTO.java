package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BillDTO {

    // ===== Thuộc tính gốc =====
    private int id;
    private Date createDay;
    private double total;
    private int idTable;
    private String employ;   // 🔥 MATAIKHOAN (VARCHAR)
    private int status;

    // ===== Thuộc tính mở rộng =====
    private double promotionPrice;
    private double customerPrice;
    private double outPrice;
    private double revenue;
    private String tableName;

    // ===== Constructor rỗng =====
    public BillDTO() {}

    // ===== Constructor đầy đủ =====
    public BillDTO(int id, Date createDay, double total,
                   int idTable, String employ, int status) {
        this.id = id;
        this.createDay = createDay;
        this.total = total;
        this.idTable = idTable;
        this.employ = employ;
        this.status = status;
    }

    // ===== Constructor từ ResultSet =====
    public BillDTO(ResultSet rs) throws SQLException {

        // BillDTO
        this.id = rs.getInt("ma");
        this.createDay = rs.getTimestamp("ngaytao");
        this.total = rs.getDouble("tongtien");
        this.idTable = rs.getInt("maban");
        this.employ = rs.getString("manhanvien"); // 🔥 VARCHAR
        this.status = rs.getInt("trangthai");

        // BillUpDTO (nếu có)
        try {
            this.promotionPrice = rs.getDouble("giauudai");
            this.customerPrice = rs.getDouble("giakhachhang");
            this.outPrice = rs.getDouble("giangoai");
            this.revenue = rs.getDouble("doanhthu");
            this.tableName = rs.getString("tenban");
        } catch (SQLException e) {
            this.tableName = "";
        }
    }

    // ===== Getter & Setter =====
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public String getEmploy() {
        return employ;
    }

    public void setEmploy(String employ) {
        this.employ = employ;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public double getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(double customerPrice) {
        this.customerPrice = customerPrice;
    }

    public double getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(double outPrice) {
        this.outPrice = outPrice;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getTableName() {
        return tableName == null ? "" : tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}