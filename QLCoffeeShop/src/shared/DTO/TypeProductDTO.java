package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeProductDTO {

    // ===== Fields =====
    private int id;          // MA
    private String nameType; // TENLOAI
    private int status;      // TRANGTHAI

    // ===== Constructor rỗng =====
    public TypeProductDTO() {
    }

    // ===== Constructor đầy đủ =====
    public TypeProductDTO(int id, String nameType, int status) {
        this.id = id;
        this.nameType = nameType;
        this.status = status;
    }

    // ===== Constructor đọc từ ResultSet (thay cho DataRow) =====
    public TypeProductDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("MA");
        this.nameType = rs.getString("TENLOAI");
        this.status = rs.getInt("TRANGTHAI");
    }

    // ===== Getter & Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

