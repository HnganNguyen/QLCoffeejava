package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BillDTO {

    // ===== Thuộc tính gốc (BillDTO) =====
    private int id;
    private Date createDay;
    private double total;
    private int idTable;
    private int employ;
    private int status;

    // ===== Thuộc tính mở rộng (BillUpDTO) =====
    private double promotionPrice;
    private double customerPrice;
    private double outPrice;
    private double revenue;
    private String tableName;

    // ===== Constructor rỗng =====
    public BillDTO() {
    }

    // ===== Constructor đầy đủ (giống C# BillDTO) =====
    public BillDTO(int id, Date createDay, double total,
                   int idTable, int employ, int status) {
        this.id = id;
        this.createDay = createDay;
        this.total = total;
        this.idTable = idTable;
        this.employ = employ;
        this.status = status;
    }

    // ===== Constructor từ ResultSet (thay cho DataRow) =====
    public BillDTO(ResultSet rs) throws SQLException {

        // BillDTO
        this.id = rs.getInt("ma");
        this.createDay = rs.getTimestamp("ngaytao");
        this.total = rs.getDouble("tongtien");
        this.idTable = rs.getInt("maban");
        this.employ = rs.getInt("manhanvien");
        this.status = rs.getInt("trangthai");

        // BillUpDTO (nếu có cột thì lấy, không có thì bỏ qua)
        try {
            this.promotionPrice = rs.getDouble("giauudai");
            this.customerPrice = rs.getDouble("giakhachhang");
            this.outPrice = rs.getDouble("giangoai");
            this.revenue = rs.getDouble("doanhthu");
            this.tableName = rs.getString("tenban");
        } catch (SQLException e) {
            // Query không có các cột BillUp → bỏ qua
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

    public int getEmploy() {
        return employ;
    }

    public void setEmploy(int employ) {
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
