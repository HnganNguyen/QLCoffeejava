package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDTO {

    private int id;
    private String nameTable;
    private int status;

    // Constructor không tham số
    public TableDTO() {
    }

    // Constructor đầy đủ tham số
    public TableDTO(int id, String nameTable, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.status = status;
    }

    // Constructor lấy dữ liệu từ ResultSet (thay cho DataRow)
    public TableDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("ma");
        this.nameTable = rs.getString("tenban");
        this.status = rs.getInt("trangthai");
    }

    // Getter & Setter
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getNameTable() {
        return nameTable;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // (Tuỳ chọn) override toString để hiển thị trong JComboBox
    @Override
    public String toString() {
        return nameTable;
    }
}

