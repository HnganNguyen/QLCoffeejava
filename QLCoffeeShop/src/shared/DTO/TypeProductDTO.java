package shared.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeProductDTO {

    private int id;          // MA
    private String nameType; // TENLOAI
    private int status;      // TRANGTHAI

    public TypeProductDTO() {}

    public TypeProductDTO(int id, String nameType, int status) {
        this.id = id;
        this.nameType = nameType;
        this.status = status;
    }

    // ⚠️ BẮT BUỘC – DAL ĐANG DÙNG
    public TypeProductDTO(ResultSet rs) throws SQLException {
        this.id = rs.getInt("MA");
        this.nameType = rs.getString("TENLOAI");
        this.status = rs.getInt("TRANGTHAI");
    }

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

    // ⭐ CỰC KỲ QUAN TRỌNG (ComboBox + Table)
    @Override
    public String toString() {
        return nameType;
    }
}
